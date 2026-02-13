package com.yonix.order_management.exceptions.OrderExceptions;

public class OrderStatusException extends RuntimeException {
    public OrderStatusException(String message) {
        super(message);
    }
}
