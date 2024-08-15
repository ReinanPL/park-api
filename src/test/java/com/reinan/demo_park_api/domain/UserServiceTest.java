package com.reinan.demo_park_api.domain;

import com.reinan.demo_park_api.entity.User;
import com.reinan.demo_park_api.exception.EntityNotFoundException;
import com.reinan.demo_park_api.exception.PasswordInvalidException;
import com.reinan.demo_park_api.exception.UsernameUniqueViolationException;
import com.reinan.demo_park_api.repository.UserRepository;
import com.reinan.demo_park_api.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.reinan.demo_park_api.common.UserConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;


    @Test
    public void saveUserWithValidData_ReturnsUser() {
        when(userRepository.save(USER)).thenReturn(USER);

        User savedUser = userService.save(USER);

        assertEquals(USER, savedUser);
        verify(userRepository, times(1)).save(USER);
    }

    @Test
    public void saveUser_WithUsernameExistent_ReturnUsernameUniqueViolation() {
        when(userRepository.save(USER)).thenThrow(new UsernameUniqueViolationException("Username already exists!"));

        assertThrows(UsernameUniqueViolationException.class, () -> userService.save(USER));
        verify(userRepository, times(1)).save(USER);
    }

    @Test
    public void getUser_ByExistingId_ReturnsUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(USER));

        User result = userService.getUser(1L);

        assertEquals(USER, result);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void getUser_ByNonExistentId_ReturnsEntityNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.getUser(1L));

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void setPassword_WithValidData() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(USER));

        userService.setPassword(1L, USER_PASSWORD.getOldPassword(), USER_PASSWORD.getNewPassword(), USER_PASSWORD.getConfirmPassword());

        assertEquals(USER_PASSWORD.getNewPassword(), USER.getPassword());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void setPassword_WithPasswordsDoNotMatch() {
        assertThrows(PasswordInvalidException.class, () -> userService.setPassword(1L, USER_PASSWORD_NOT_MATCH.getOldPassword(), USER_PASSWORD_NOT_MATCH.getNewPassword(), USER_PASSWORD_NOT_MATCH.getConfirmPassword()));
        verify(userRepository, never()).findById(anyLong());
    }

    @Test
    public void testSetPassword_WithOldPasswordDoesNotMatch() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(USER));

        assertThrows(PasswordInvalidException.class, () -> userService.setPassword(1L, USER_PASSWORD_OLD_NOT_MATCH.getOldPassword(), USER_PASSWORD_OLD_NOT_MATCH.getNewPassword(), USER_PASSWORD_OLD_NOT_MATCH.getConfirmPassword()));
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void listUser_ReturnAllPlanets() {
        when(userRepository.findAll()).thenReturn(USERS);

        List<User> result = userService.getAllUsers();

        assertEquals(USERS, result);
        verify(userRepository, times(1)).findAll();
    }
    @Test
    public void listUser_ReturnNoUsers() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<User> result = userService.getAllUsers();

        assertThat(result).isEmpty();
        verify(userRepository, times(1)).findAll();
    }
}


