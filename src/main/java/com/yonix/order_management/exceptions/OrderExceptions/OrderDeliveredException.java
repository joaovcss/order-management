package com.yonix.order_management.exceptions.OrderExceptions;

public class OrderDeliveredException extends RuntimeException {
    public OrderDeliveredException(){
        super("The order is on it's way, cannot do this action!");
    }

    public OrderDeliveredException(String message){
        super(message);
    }
}
