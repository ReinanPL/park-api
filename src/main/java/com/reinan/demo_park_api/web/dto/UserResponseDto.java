package com.reinan.demo_park_api.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @RequiredArgsConstructor @ToString
public class UserResponseDto {

    private Long id;
    private String username;
    private String role;
}
