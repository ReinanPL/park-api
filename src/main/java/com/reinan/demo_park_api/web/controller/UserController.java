package com.reinan.demo_park_api.web.controller;

import com.reinan.demo_park_api.entity.User;
import com.reinan.demo_park_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        return ResponseEntity.ok().body(userService.getUser(id));
    }

    @PatchMapping(path = "{id}")
    public ResponseEntity<User> setPassword(@PathVariable Long id, @RequestBody User user){
        return ResponseEntity.ok().body(userService.setPassword(id, user.getPassword()));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

}
