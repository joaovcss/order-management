package com.yonix.order_management.exceptions.ProductExceptions;

public class UpdateProductException extends RuntimeException {

    public UpdateProductException(String message) {
        super(message);
    }

    public UpdateProductException() {
        super("error updating product");
    }
}
