package com.yonix.order_management.dto.request;

import java.util.UUID;

public record OrderItemRequest (UUID productId, Integer quantity){}
