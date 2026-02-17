package com.yonix.order_management.exceptions.ProductExceptions;

public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException() {
        super("insufficient stock");
    }
}
