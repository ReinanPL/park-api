package com.reinan.demo_park_api.web.controller.impl;

import com.reinan.demo_park_api.security.AuthenticationService;
import com.reinan.demo_park_api.entity.User;
import com.reinan.demo_park_api.web.controller.AuthenticateController;
import com.reinan.demo_park_api.web.dto.UserLoginRequest;
import com.reinan.demo_park_api.web.dto.UserTokenResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "Resource for authenticating user")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1")
public class AuthenticateControllerImpl implements AuthenticateController {

    private final AuthenticationService authenticationService;

    @Override
    @PostMapping("/login")
    public ResponseEntity<UserTokenResponse> authenticateUser(@RequestBody @Valid UserLoginRequest loginRequest) {
        User userChecked = authenticationService.checkUserToken(loginRequest);
        UserTokenResponse responseToken = authenticationService.generateToken(userChecked);
        return ResponseEntity.ok().body(responseToken);
    }
}
