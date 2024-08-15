package com.reinan.demo_park_api.web.controller;

import com.reinan.demo_park_api.service.UserService;
import com.reinan.demo_park_api.web.dto.UserCreateDto;
import com.reinan.demo_park_api.web.dto.UserPasswordDto;
import com.reinan.demo_park_api.web.dto.UserResponseDto;
import com.reinan.demo_park_api.web.dto.mapper.UserMapper;
import com.reinan.demo_park_api.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User", description = "Contains all operations related to resources for registering, editing and reading a user.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;


    @Operation(summary = "Create a new user", description = "Resource for creating a new user",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Resource created with success",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "User email already registered in the system",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Resource not processed due to invalid input data",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserCreateDto userDto) {
        var userSaved = userService.save(UserMapper.toUser(userDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(userSaved));
    }


    @Operation(summary = "Retrieve a user by id", description = "Retrieve a user by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Resource not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping(path = "{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id){
        var user = userService.getUser(id);
        return ResponseEntity.ok().body(UserMapper.toDto(user));
    }


    @Operation(summary = "Update password", description = "Update password",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Password updated successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "400", description = "Password does not match",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Resource not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PatchMapping(path = "{id}")
    public ResponseEntity<Void> setPassword(@PathVariable Long id, @RequestBody @Valid UserPasswordDto passwordDto){
        userService.setPassword(id, passwordDto.getOldPassword(), passwordDto.getNewPassword(), passwordDto.getConfirmPassword());
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "List all users", description = "List all registered users",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of all registered users",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UserResponseDto.class))))
            })
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers(){
        var users = userService.getAllUsers();
        return ResponseEntity.ok().body(UserMapper.toDtoList(users));
    }

}
