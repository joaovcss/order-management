package com.yonix.order_management.service;

import com.yonix.order_management.dto.request.CreateUserRequest;
import com.yonix.order_management.entity.User;
import com.yonix.order_management.exceptions.UserExceptions.UserNotFoundException;
import com.yonix.order_management.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll(){
        List<User> users = userRepository.findAll();
        if(users.isEmpty()){
            throw new UserNotFoundException("there is no users registered");
        }
        return users;
    }

    public User findById(UUID id){
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    public User create(CreateUserRequest request){
        User user = User.create(request);
        return userRepository.save(user);
    }

    public void delete(UUID id){
        if(userRepository.findById(id).isEmpty()){
            throw new UserNotFoundException();
        }
        userRepository.deleteById(id);
    }
}
