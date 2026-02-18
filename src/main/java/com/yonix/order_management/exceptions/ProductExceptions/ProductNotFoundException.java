package com.yonix.order_management.exceptions.ProductExceptions;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message){
        super(message);
    }
    public ProductNotFoundException() {
        super("Product not found");
    }
}
