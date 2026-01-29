package com.yonix.order_management.service;

import com.yonix.order_management.dto.request.OrderItemRequest;
import com.yonix.order_management.entity.*;
import com.yonix.order_management.repository.OrderRepository;
import com.yonix.order_management.repository.ProductRepository;
import com.yonix.order_management.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public List<Order> findAll(){
        return orderRepository.findAll();
    }

    public Order findById(UUID id){
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("order not found"));
    }

    @Transactional
    public Order createOrder(UUID userId, List<OrderItemRequest> items){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.CREATED);
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (OrderItemRequest item : items) {
            UUID productId = item.productId();
            Integer quantity = item.quantity();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("product not found"));

            if(product.getStock() < quantity){
                throw new RuntimeException("insufficient stock");
            }
            product.setStock(product.getStock() - quantity);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItem.setUnitPrice(product.getPrice());

            BigDecimal itemTotal = product.getPrice()
                            .multiply(BigDecimal.valueOf(quantity));
            totalPrice = totalPrice.add(itemTotal);

            orderItems.add(orderItem);
        };
        order.setItems(orderItems);
        return orderRepository.save(order);
    }

    @Transactional
    public Order cancelOrder(UUID orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("order not found"));

        if(order.getStatus().equals(OrderStatus.SHIPPED)){
            throw new RuntimeException("cannot cancel an order that already has been shipped");
        }
        if(order.getStatus().equals(OrderStatus.CANCELED)){
            throw new RuntimeException("cannot cancel an order that already has been cancelled");
        }
        if(order.getStatus().equals(OrderStatus.PAID)){
            throw new RuntimeException("cannot cancel an order that already has been paid");
        }
        if(order.getStatus().equals(OrderStatus.DELIVERED)){
            throw new RuntimeException("cannot cancel an order that already has been delivered");
        }

        order.setStatus(OrderStatus.CANCELED);
        order.getItems().forEach(item -> {
            Product product = item.getProduct();
            Integer quantity = item.getQuantity();

            product.setStock(product.getStock() + quantity);
        });
        return orderRepository.save(order);
    }

}
