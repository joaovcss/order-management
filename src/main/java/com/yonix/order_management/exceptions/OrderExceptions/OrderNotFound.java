package com.yonix.order_management.exceptions.OrderExceptions;

public class OrderNotFound extends RuntimeException {

    public OrderNotFound() {
        super("Order not found");
    }
}
