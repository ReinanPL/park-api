package com.reinan.demo_park_api.web.controller;

import com.reinan.demo_park_api.service.UserService;
import com.reinan.demo_park_api.web.dto.UserCreateDto;
import com.reinan.demo_park_api.web.dto.UserPasswordDto;
import com.reinan.demo_park_api.web.dto.UserResponseDto;
import com.reinan.demo_park_api.web.dto.mapper.UserMapper;
import jakarta.validation.Valid;
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
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserCreateDto userDto) {
        var userSaved = userService.save(UserMapper.toUser(userDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(userSaved));
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id){
        var user = userService.getUser(id);
        return ResponseEntity.ok().body(UserMapper.toDto(user));
    }

    @PatchMapping(path = "{id}")
    public ResponseEntity<Void> setPassword(@PathVariable Long id, @RequestBody @Valid UserPasswordDto passwordDto){
        userService.setPassword(id, passwordDto.getOldPassword(), passwordDto.getNewPassword(), passwordDto.getConfirmPassword());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers(){
        var users = userService.getAllUsers();
        return ResponseEntity.ok().body(UserMapper.toDtoList(users));
    }

}
