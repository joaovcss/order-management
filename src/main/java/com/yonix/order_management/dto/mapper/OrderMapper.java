package com.yonix.order_management.dto.mapper;

import com.yonix.order_management.dto.response.OrderItemResponse;
import com.yonix.order_management.dto.response.OrderResponse;
import com.yonix.order_management.entity.Order;
import com.yonix.order_management.entity.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public class OrderMapper {

    private OrderMapper(){ }

    public static OrderResponse toResponse(Order order){
        return new OrderResponse(
                order.getId(),
                order.getUser().getId(),
                toItemResponseList(order.getItems()),
                order.getStatus(),
                order.getTotal()
        );
    }

    public static List<OrderItemResponse> toItemResponseList(List<OrderItem> items){
        return items.stream()
                .map(OrderMapper::toItemResponse)
                .toList();
    }

    public static OrderItemResponse toItemResponse(OrderItem item){
        return new OrderItemResponse(
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
        );
    }
}
