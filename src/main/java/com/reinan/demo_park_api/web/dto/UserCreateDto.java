package com.reinan.demo_park_api.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @RequiredArgsConstructor @ToString
public class UserCreateDto {

    private String username;
    private String password;
}
