package com.yonix.order_management.dto.request;


import com.yonix.order_management.entity.UserRole;

public record CreateUserRequest(String name, String login, String password, String role) {
}
