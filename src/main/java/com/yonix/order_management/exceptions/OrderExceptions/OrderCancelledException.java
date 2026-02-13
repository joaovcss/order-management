package com.yonix.order_management.exceptions.OrderExceptions;

public class OrderCancelledException extends RuntimeException
{
    public OrderCancelledException(String message)
    {
        super(message);
    }

    public  OrderCancelledException()
    {
        super("The order is cancelled, cannot do this action!");
    }
}
