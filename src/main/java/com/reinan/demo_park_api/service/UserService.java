package com.reinan.demo_park_api.service;

import com.reinan.demo_park_api.entity.User;
import com.reinan.demo_park_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public void setPassword(Long id, String oldPassword, String newPassword, String confirmPassword){
        Optional.of(newPassword)
                .filter(p -> p.equals(confirmPassword))
                .orElseThrow(() -> new RuntimeException("New password does not match with confirm password."));

        var user = getUser(id);
        Optional.of(user)
                .filter(u -> oldPassword.equals(u.getPassword()))
                .orElseThrow(() -> new RuntimeException("Old password does not match."));

        user.setPassword(newPassword);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
