package com.reinan.demo_park_api.web.controller;


import com.reinan.demo_park_api.web.dto.UserLoginRequest;
import com.reinan.demo_park_api.web.dto.UserTokenResponse;
import com.reinan.demo_park_api.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Tag(name = "Authentication", description = "Resource for authenticating user")
public interface AuthenticateController {

    @Operation(summary = "Authenticate a user", description = "Resource for authenticating an existing user and generating a JWT token.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User authenticated successfully. Returns a JWT token.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserTokenResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid credentials. User authentication failed.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Invalid fields. User authentication failed.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping("/login")
    ResponseEntity<UserTokenResponse> authenticateUser(@RequestBody @Valid UserLoginRequest loginRequest);
}