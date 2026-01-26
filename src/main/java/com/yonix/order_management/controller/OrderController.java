package com.yonix.order_management.controller;

import com.yonix.order_management.dto.OrderItemRequest;
import com.yonix.order_management.entity.Order;
import com.yonix.order_management.service.OrderService;
import org.aspectj.weaver.ast.Or;
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
    public ResponseEntity<List<Order>> findAll(){
        List<Order> orders = orderService.findAll();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findById(@PathVariable UUID id){
        Order order = orderService.findById(id);
        return ResponseEntity.ok(order);
    }


}
