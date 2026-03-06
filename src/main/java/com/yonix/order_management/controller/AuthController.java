package com.yonix.order_management.controller;

import com.yonix.order_management.dto.mapper.UserMapper;
import com.yonix.order_management.dto.request.CreateUserRequest;
import com.yonix.order_management.dto.request.LoginRequest;
import com.yonix.order_management.dto.request.RegisterRequest;
import com.yonix.order_management.dto.response.LoginResponse;
import com.yonix.order_management.dto.response.UserResponse;
import com.yonix.order_management.entity.User;
import com.yonix.order_management.repository.UserRepository;
import com.yonix.order_management.service.AuthService;
import com.yonix.order_management.service.TokenService;
import com.yonix.order_management.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserService userService;
    private UserRepository userRepository;
    private final AuthService authService;

    public AuthController(UserService userService, UserRepository userRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest data){
        var token = authService.login(data);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid RegisterRequest data) {
        if (this.userRepository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User user = userService.create(new CreateUserRequest(data.name(), data.login(), encryptedPassword, data.role()));

        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toUserResponse(user));
    }
}
