package com.reinan.demo_park_api.domain;

import com.reinan.demo_park_api.entity.Client;
import com.reinan.demo_park_api.entity.User;
import com.reinan.demo_park_api.exception.CpfUniqueViolationException;
import com.reinan.demo_park_api.exception.EntityNotFoundException;
import com.reinan.demo_park_api.repository.ClientRepository;
import com.reinan.demo_park_api.repository.projection.ClientProjection;
import com.reinan.demo_park_api.service.UserService;
import com.reinan.demo_park_api.service.impl.ClientServiceImpl;
import com.reinan.demo_park_api.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static com.reinan.demo_park_api.common.ClientConstants.*;
import static com.reinan.demo_park_api.common.UserConstants.USER;
import static com.reinan.demo_park_api.common.UserConstants.USER2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private UserService userService;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    public void saveUserWithValidData_ReturnsUser() {
        when(clientRepository.save(CLIENT)).thenReturn(CLIENT);

        Client savedClient = clientService.saveClient(CLIENT);

        assertEquals(CLIENT, savedClient);
        verify(clientRepository, times(1)).save(CLIENT);
    }

    @Test
    public void saveUser_WithCpfExistent_ReturnUsernameUniqueViolation() {
        when(clientRepository.save(CLIENT)).thenThrow(new CpfUniqueViolationException("Cpf already exists!"));

        assertThrows(CpfUniqueViolationException.class, () -> clientService.saveClient(CLIENT));
        verify(clientRepository, times(1)).save(CLIENT);
    }

    @Test
    public void listUser_ReturnAllUsers() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<ClientProjection> expectedPage = new PageImpl<>(CLIENTS, pageable, CLIENTS.size());
        when(clientRepository.findAllPageable(pageable)).thenReturn(expectedPage);

        Page<ClientProjection> result = clientService.getAllClients(pageable);

        assertEquals(expectedPage, result);
        verify(clientRepository, times(1)).findAllPageable(pageable);
    }

    @Test
    void getAllClients_shouldReturnEmptyPageWhenNoClientsFound() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<ClientProjection> expectedPage = Page.empty(pageable);
        when(clientRepository.findAllPageable(pageable)).thenReturn(expectedPage);

        Page<ClientProjection> result = clientService.getAllClients(pageable);

        assertEquals(expectedPage, result);
        verify(clientRepository, times(1)).findAllPageable(pageable);
    }

    @Test
    void getClientByUserId_shouldReturnClientWhenFound() {
        Long userId = 1L;
        USER2.setId(userId);

        when(clientRepository.findByUserId(userId)).thenReturn(CLIENT2);

        TestingAuthenticationToken authentication = new TestingAuthenticationToken(userId.toString(), null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(userService.getUser(userId)).thenReturn(USER2);

        Client result = clientService.getClientByUserId();

        assertEquals(CLIENT2, result);
        SecurityContextHolder.clearContext();
    }

    @Test
    public void getUser_ByExistingId_ReturnsUser() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(CLIENT));

        Client result = clientService.getClientById(1L);

        assertEquals(CLIENT, result);
        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    public void getUser_ByNonExistentId_ReturnsEntityNotFound() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> clientService.getClientById(1L));

        verify(clientRepository, times(1)).findById(1L);
    }


}
