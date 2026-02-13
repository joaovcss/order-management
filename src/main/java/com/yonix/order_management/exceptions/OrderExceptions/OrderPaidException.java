package com.yonix.order_management.exceptions.OrderExceptions;

public class OrderPaidException extends RuntimeException{
    public OrderPaidException(){
        super("The order is paid, cannot do this action!");
    }

    public OrderPaidException(String message){
        super(message);
    }
}
