package com.yonix.order_management.dto.mapper;

import com.yonix.order_management.dto.response.UserResponse;
import com.yonix.order_management.entity.User;

public class UserMapper {

    public static UserResponse toUserResponse(User user){
        return new UserResponse(
                user.getId(),
                user.getName()
        );
    }
}
