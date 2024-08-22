package com.reinan.demo_park_api.web.controller.impl;

import com.reinan.demo_park_api.entity.Client;
import com.reinan.demo_park_api.repository.projection.ClientProjection;
import com.reinan.demo_park_api.service.ClientService;
import com.reinan.demo_park_api.service.impl.UserServiceImpl;
import com.reinan.demo_park_api.web.controller.ClientController;
import com.reinan.demo_park_api.web.dto.ClientCreateDto;
import com.reinan.demo_park_api.web.dto.ClientResponseDto;
import com.reinan.demo_park_api.web.dto.PageableDto;
import com.reinan.demo_park_api.web.dto.mapper.ClientMapper;
import com.reinan.demo_park_api.web.dto.mapper.PageableMapper;
import com.reinan.demo_park_api.web.dto.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/clients")
public class ClientControllerImpl implements ClientController {

    private final ClientService clientService;


    @Override
    @PreAuthorize("hasAuthority('SCOPE_ROLE_CLIENT')")
    @PostMapping
    public ResponseEntity<ClientResponseDto> saveClient(@RequestBody @Valid ClientCreateDto dto) {

        Client clientSaved = clientService.saveClient(ClientMapper.toClient(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(ClientMapper.toDto(clientSaved));
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @GetMapping("{id}")
    public ResponseEntity<ClientResponseDto> getClientById(@PathVariable Long id) {
        return ResponseEntity.ok(ClientMapper.toDto(clientService.getClientById(id)));
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<PageableDto> getAllClients(Pageable pageable) {
        Page<ClientProjection> clients = clientService.getAllClients(pageable);
        return ResponseEntity.ok(PageableMapper.toDto(clients));
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ROLE_CLIENT')")
    @GetMapping("/details")
    public ResponseEntity<ClientResponseDto> getClientDetails() {
        return ResponseEntity.ok(ClientMapper.toDto(clientService.getClientByUserId()));
    }

}
