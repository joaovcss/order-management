package com.yonix.order_management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yonix.order_management.dto.request.CreateUserRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    private String name;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Order> orders;

    public static User create(CreateUserRequest request) {
        User user = new User();
        user.name = request.name();
        return user;
    }
}
