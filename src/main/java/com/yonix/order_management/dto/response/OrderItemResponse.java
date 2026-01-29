package com.yonix.order_management.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemResponse(
    UUID productId,
    String productName,
    Integer quantity,
    BigDecimal unitaryPrice,
    BigDecimal subtotal
) { }
