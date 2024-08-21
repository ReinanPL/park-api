package com.reinan.demo_park_api.service;

import com.reinan.demo_park_api.entity.User;
import java.util.List;

public interface UserService {
    User save(User user);
    User getUser(Long id);
    void setPassword(Long id, String oldPassword, String newPassword, String confirmPassword);
    List<User> getAllUsers();
}
