package com.yonix.order_management.service;

import com.yonix.order_management.dto.request.CreateUserRequest;
import com.yonix.order_management.dto.request.LoginRequest;
import com.yonix.order_management.dto.request.RegisterRequest;
import com.yonix.order_management.entity.User;
import com.yonix.order_management.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final UserService userService;

    public AuthService(
            AuthenticationManager authenticationManager,
            TokenService tokenService,
            UserRepository userRepository,
            UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public String login(LoginRequest data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        return tokenService.generateToken((User) auth.getPrincipal());
    }

    public User register(RegisterRequest data) {
        if (this.userRepository.findByLogin(data.login()) != null) {
            throw new IllegalArgumentException("User with this login already exists");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        return userService.create(new CreateUserRequest(data.name(), data.login(), encryptedPassword, data.role()));
    }
}
