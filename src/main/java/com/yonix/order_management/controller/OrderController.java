package com.yonix.order_management.controller;

import com.yonix.order_management.dto.mapper.OrderMapper;
import com.yonix.order_management.dto.request.CreateOrderRequest;
import com.yonix.order_management.dto.response.OrderResponse;
import com.yonix.order_management.entity.Order;
import com.yonix.order_management.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<OrderResponse>> findAll(){
        List<OrderResponse> orders = orderService.findAll();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findById(@PathVariable UUID id){
        Order order = orderService.findById(id);
        return ResponseEntity.ok(order);
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody @Valid CreateOrderRequest request){
        Order saved = orderService.createOrder(request.userId(), request.items());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(OrderMapper.toResponse(saved));
    }

    @PatchMapping("/cancel/{orderId}")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable UUID orderId){
        Order order = orderService.cancelOrder(orderId);
        return ResponseEntity.ok(OrderMapper.toResponse(order));
    }

    @PatchMapping("/pay/{orderId}")
    public ResponseEntity<OrderResponse> payOrder(@PathVariable UUID orderId){
        Order order = orderService.payOrder(orderId);
        return ResponseEntity.ok(OrderMapper.toResponse(order));
    }

    @PatchMapping("/send/{orderId}")
    public ResponseEntity<OrderResponse> sendOrder(@PathVariable UUID orderId){
        Order order = orderService.sendOrder(orderId);
        return ResponseEntity.ok(OrderMapper.toResponse(order));
    }

    @PatchMapping("/deliver/{orderId}")
    public ResponseEntity<OrderResponse> deliverOrder(@PathVariable UUID orderId){
        Order order = orderService.deliverOrder(orderId);
        return ResponseEntity.ok(OrderMapper.toResponse(order));
    }
}
