package com.yonix.order_management.exceptions.OrderExceptions;

public class OrderCancelledException extends OrderStatusException {

    public OrderCancelledException(String message)
    {
        super(message);
    }

    public  OrderCancelledException()
    {
        super("The order is cancelled, cannot do this action!");
    }
}
