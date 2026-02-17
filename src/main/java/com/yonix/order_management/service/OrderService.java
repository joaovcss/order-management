package com.yonix.order_management.service;

import com.yonix.order_management.dto.mapper.OrderMapper;
import com.yonix.order_management.dto.request.OrderItemRequest;
import com.yonix.order_management.dto.response.OrderResponse;
import com.yonix.order_management.entity.*;
import com.yonix.order_management.exceptions.OrderExceptions.*;
import com.yonix.order_management.exceptions.ProductExceptions.ProductNotFoundException;
import com.yonix.order_management.exceptions.UserExceptions.UserNotFoundException;
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

    public List<OrderResponse> findAll(){
        return orderRepository.findAllOrders()
                .stream()
                .map(OrderMapper::toResponse)
                .toList();
    }

    public Order findById(UUID id){
        return orderRepository.findById(id)
                .orElseThrow(OrderNotFoundException::new);
    }

    @Transactional
    public Order createOrder(UUID userId, List<OrderItemRequest> items){
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Order order = Order.create(user);

        for(OrderItemRequest item : items){
            UUID productId = item.productId();
            Integer quantity = item.quantity();

            Product product = productRepository.findById(productId)
                    .orElseThrow(ProductNotFoundException::new);

            order.addItem(product, quantity);
        }
        return orderRepository.save(order);
    }

    @Transactional
    public Order cancelOrder(UUID orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);
        order.cancel();
        order.getItems().forEach(item -> {
            Product product = item.getProduct();
            Integer quantity = item.getQuantity();

            product.setStock(product.getStock() + quantity);
        });
        return orderRepository.save(order);
    }

    @Transactional
    public Order payOrder(UUID orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);
        order.pay();
        return orderRepository.save(order);
    }

    @Transactional
    public Order sendOrder(UUID orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);
        order.send();
        return orderRepository.save(order);
    }

    @Transactional
    public Order deliverOrder(UUID orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);
        order.deliver();
        return orderRepository.save(order);
    }
}
