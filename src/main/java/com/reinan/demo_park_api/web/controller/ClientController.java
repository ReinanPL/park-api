package com.reinan.demo_park_api.web.controller;

import com.reinan.demo_park_api.entity.Client;
import com.reinan.demo_park_api.web.dto.ClientCreateDto;
import com.reinan.demo_park_api.web.dto.ClientResponseDto;
import com.reinan.demo_park_api.web.dto.PageableDto;
import com.reinan.demo_park_api.web.dto.UserResponseDto;
import com.reinan.demo_park_api.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@Tag(name = "Client", description = "Contains all operations related to resources for registering, editing and reading a client.")
public interface ClientController {

    @Operation(summary = "Create a new client", description = "Resource for creating a new client.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Resource created with success.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Client cpf already registered in the system.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Resource not processed due to invalid input data.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Resource not allowed for admin profile.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    ResponseEntity<ClientResponseDto> saveClient(@RequestBody @Valid ClientCreateDto dto);

    @Operation(summary = "Retrieve a Client by id", description = "Access restricted to ADMIN.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource retrieved successfully.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Client don't have permission to access this resource.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Resource not found.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    ResponseEntity<ClientResponseDto> getClientById(@PathVariable Long id);

    @Operation(summary = "List all clients registered", description = "Access restricted to ADMIN.",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(in = QUERY, name = "page",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "0")), description = "Page number"),
                    @Parameter(in = QUERY  ,name = "size",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "20")), description = "Number of items per page"),
                    @Parameter(in = QUERY, name = "sort", hidden = true,
                            array = @ArraySchema(schema = @Schema(type = "string", defaultValue = "id,asc")),
                            description = "Represents the ordering of results. Accepts multiple sorting criteria"),

            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of all registered clients.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Client without permission to access this resource.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    ResponseEntity<PageableDto> getAllClients(@Parameter(hidden = true) @PageableDefault(size = 5, sort = {"name"}) Pageable pageable);

    @Operation(summary = "Retrieve Client authenticated ", description = "Access restricted to CLIENT.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource retrieved successfully.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "ADMIN don't have permission to access this resource.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            })
    ResponseEntity<ClientResponseDto> getClientDetails();
}
