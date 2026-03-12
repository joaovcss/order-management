package com.yonix.order_management.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequest(@NotNull @NotBlank String name, @NotNull @NotBlank String login, @NotNull @NotBlank String password) {
}