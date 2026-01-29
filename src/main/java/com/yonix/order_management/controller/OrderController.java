package com.yonix.order_management.controller;

import com.yonix.order_management.dto.request.CreateOrderRequest;
import com.yonix.order_management.dto.request.OrderItemRequest;
import com.yonix.order_management.dto.response.OrderItemResponse;
import com.yonix.order_management.dto.response.OrderResponse;
import com.yonix.order_management.entity.Order;
import com.yonix.order_management.entity.OrderItem;
import com.yonix.order_management.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<Order>> findAll(){
        List<Order> orders = orderService.findAll();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findById(@PathVariable UUID id){
        Order order = orderService.findById(id);
        return ResponseEntity.ok(order);
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody CreateOrderRequest request){
        Order saved = orderService.createOrder(request.userId(), request.items());

        List<OrderItemResponse> items = saved.getItems().stream()
                .map(item -> new OrderItemResponse(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
                ))
                .toList();

        OrderResponse orderResponse = new OrderResponse(
                saved.getId(),
                saved.getUser().getId(),
                items,
                saved.getStatus(),
                saved.getTotal()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderResponse);
    }
}
