package com.yonix.order_management.exceptions.OrderExceptions;

public class OrderShippedException extends OrderStatusException {

    public OrderShippedException(){
        super("The order is on it's way, cannot do this action!");
    }

    public OrderShippedException(String message){
        super(message);
    }
}
