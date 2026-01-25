package com.yonix.order_management.service;

import com.yonix.order_management.entity.User;
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
        return userRepository.findAll();
    }

    public User findById(UUID id){
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("user not found"));
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public void delete(UUID id){
        if(userRepository.findById(id).isEmpty()){
            throw new RuntimeException("user not found");
        }
        userRepository.deleteById(id);
    }
}
