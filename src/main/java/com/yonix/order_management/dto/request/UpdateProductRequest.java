package com.yonix.order_management.dto.request;

import java.math.BigDecimal;

public record UpdateProductRequest(String name, String description, BigDecimal price, Integer stock) {
}
