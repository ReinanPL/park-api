package com.reinan.demo_park_api.web.controller;

import com.reinan.demo_park_api.web.dto.UserCreateDto;
import com.reinan.demo_park_api.web.dto.UserPasswordDto;
import com.reinan.demo_park_api.web.dto.UserResponseDto;
import com.reinan.demo_park_api.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User", description = "Contains all operations related to resources for registering, editing and reading a user.")
public interface UserController {

    @Operation(summary = "Create a new user", description = "Resource for creating a new user.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Resource created with success.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "User email already registered in the system.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Resource not processed due to invalid input data.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserCreateDto userDto);


    @Operation(summary = "Retrieve a user by id", description = "Request requires a Bearer Token. Access restricted to ADMIN||Client with match Id.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource retrieved successfully.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "User without permission to access this resource.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Resource not found.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id);


    @Operation(summary = "Update password", description = "Request requires a Bearer Token. Access restricted to ADMIN||Client with match Id.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Password updated successfully. Return no content.",
                            content = @Content(schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "400", description = "Password does not match.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "User without permission to access this resource.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "invalid or bad formatted fields",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    ResponseEntity<Void> setPassword(@PathVariable Long id, @RequestBody @Valid UserPasswordDto passwordDto);


    @Operation(summary = "List all users registered", description = "Request requires a Bearer Token. Access restricted to ADMIN.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of all registered users.",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserResponseDto.class)))),
                    @ApiResponse(responseCode = "403", description = "User without permission to access this resource.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    public ResponseEntity<List<UserResponseDto>> getAllUsers();
}
