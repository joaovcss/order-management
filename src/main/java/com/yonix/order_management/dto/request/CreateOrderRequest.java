package com.yonix.order_management.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record CreateOrderRequest(@NotNull UUID userId, @NotNull List<OrderItemRequest> items) {
}
