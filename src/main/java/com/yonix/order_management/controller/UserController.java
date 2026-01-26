package com.yonix.order_management.controller;

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
    public ResponseEntity<User> create(@RequestBody User userData){
        User user = userService.save(userData);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
