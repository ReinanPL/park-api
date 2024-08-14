package com.reinan.demo_park_api.service;

import com.reinan.demo_park_api.entity.User;
import com.reinan.demo_park_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getUser(Long id){
        return userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found"));
    }

    @Transactional
    public User setPassword(Long id, String password){
        User user = getUser(id);
        user.setPassword(password);
        return user;
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
