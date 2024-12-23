package com.socialnetwork.social_network.Service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.socialnetwork.social_network.DTO.RegisterRequest;
import com.socialnetwork.social_network.Model.User;
import com.socialnetwork.social_network.Repository.UserRepository;


class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_Success() {
        // Arrange
        RegisterRequest userDto = new RegisterRequest();
        userDto.setUsername("testuser");
        userDto.setEmail("test@example.com");
        userDto.setPassword("password123");

        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);

        // Act
        assertDoesNotThrow(() -> authService.registerUser(userDto));

        // Verify
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerUser_EmailAlreadyExists() {
        // Arrange
        RegisterRequest userDto = new RegisterRequest();
        userDto.setEmail("duplicate@example.com");

        when(userRepository.existsByEmail("duplicate@example.com")).thenReturn(true);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> authService.registerUser(userDto));
        assertEquals("Email is already in use", exception.getMessage());

        // Verify
        verify(userRepository, never()).save(any(User.class));
    }
}
