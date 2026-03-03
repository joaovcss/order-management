package com.yonix.order_management.dto.response;

import java.util.UUID;

public record UserResponse(UUID id, String name, String login, String role) {
}
