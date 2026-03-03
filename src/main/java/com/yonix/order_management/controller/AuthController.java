package com.yonix.order_management.controller;

import com.yonix.order_management.dto.mapper.UserMapper;
import com.yonix.order_management.dto.request.CreateUserRequest;
import com.yonix.order_management.dto.request.LoginRequest;
import com.yonix.order_management.dto.request.RegisterRequest;
import com.yonix.order_management.dto.response.LoginResponse;
import com.yonix.order_management.dto.response.UserResponse;
import com.yonix.order_management.entity.User;
import com.yonix.order_management.repository.UserRepository;
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

    private final TokenService tokenService;
    private AuthenticationManager authenticationManager;
    private UserService userService;
    private UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, UserRepository userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginRequest data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

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
