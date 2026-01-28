package com.yonix.order_management.dto;

import java.util.List;
import java.util.UUID;

public record CreateOrderRequest(UUID userId, List<OrderItemRequest> items) {
}
