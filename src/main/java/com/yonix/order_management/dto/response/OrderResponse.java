package com.yonix.order_management.dto.response;

import com.yonix.order_management.entity.OrderStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        UUID userId,
        List<OrderItemResponse> items,
        OrderStatus status,
        BigDecimal total
) { }
