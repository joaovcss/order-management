package com.yonix.order_management.dto;

import java.math.BigDecimal;

public record CreateProductRequest(String name, String description, BigDecimal price, Integer stock) {
}
