package com.reinan.demo_park_api.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class UserCreateDto {

    @NotBlank
    @Email(message = "Email format is invalid", regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    private String username;

    @NotBlank
    @Size(min = 6, message = "Minimum of 6 characters")
    private String password;
}
