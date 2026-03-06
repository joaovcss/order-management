package com.yonix.order_management.controller;

import com.yonix.order_management.dto.mapper.UserMapper;
import com.yonix.order_management.dto.request.LoginRequest;
import com.yonix.order_management.dto.request.RegisterRequest;
import com.yonix.order_management.dto.response.LoginResponse;
import com.yonix.order_management.dto.response.UserResponse;
import com.yonix.order_management.entity.User;
import com.yonix.order_management.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest data){
        var token = authService.login(data);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid RegisterRequest data) {
        User user = authService.register(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toUserResponse(user));
    }
}
