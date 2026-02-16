package com.yonix.order_management.exceptions.OrderExceptions;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException() {
        super("Order not found");
    }
}
