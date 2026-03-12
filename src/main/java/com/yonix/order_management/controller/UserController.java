package com.yonix.order_management.controller;

import com.yonix.order_management.dto.mapper.UserMapper;
import com.yonix.order_management.dto.request.CreateUserRequest;
import com.yonix.order_management.dto.response.UserResponse;
import com.yonix.order_management.entity.User;
import com.yonix.order_management.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> showAll(){
        List<UserResponse> users = userService.findAll()
                .stream()
                .map(UserMapper::toUserResponse)
                .toList();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
