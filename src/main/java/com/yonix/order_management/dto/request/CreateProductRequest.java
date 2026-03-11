package com.yonix.order_management.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateProductRequest(@NotNull @NotBlank String name, String description, @NotNull @DecimalMin("0.0") BigDecimal price, @PositiveOrZero Integer stock) {
}
