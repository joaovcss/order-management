package com.yonix.order_management.controller;

import com.yonix.order_management.dto.request.CreateUserRequest;
import com.yonix.order_management.dto.response.UserResponse;
import com.yonix.order_management.entity.User;
import com.yonix.order_management.service.UserService;
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
    public ResponseEntity<List<User>> showAll(){
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody CreateUserRequest userData){
        User user = new User(null, userData.name(), null);
        User saved = userService.save(user);
        UserResponse userResponse = new UserResponse(
                saved.getId(),
                saved.getName()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
