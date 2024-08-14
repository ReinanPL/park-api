package com.reinan.demo_park_api.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @RequiredArgsConstructor @ToString
public class UserPasswordDto {

    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
