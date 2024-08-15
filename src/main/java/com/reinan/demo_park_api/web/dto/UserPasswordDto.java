package com.reinan.demo_park_api.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class UserPasswordDto {

    @NotBlank
    @Size(min = 6, message = "Minimum of 6 characters")
    private String oldPassword;

    @NotBlank
    @Size(min = 6, message = "Minimum of 6 characters")
    private String newPassword;

    @NotBlank
    @Size(min = 6, message = "Minimum of 6 characters")
    private String confirmPassword;
}
