package com.startnow.blog.service_tests;

import com.startnow.blog.entity.UserEntity;
import com.startnow.blog.exception.ResourceNotFoundException;
import com.startnow.blog.exception.ServiceException;
import com.startnow.blog.exception.UserAlreadyExistsException;
import com.startnow.blog.model.user_model.LoginRequest;
import com.startnow.blog.model.user_model.RegisterRequest;
import com.startnow.blog.model.user_model.User;
import com.startnow.blog.repository.IUserRepository;
import com.startnow.blog.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private UserEntity userEntity;
    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        userEntity = UserEntity.builder().id("1").name("Test User").email("test@example.com")
                .password("hashedPassword").build();

        registerRequest = new RegisterRequest("Test User", "test@example.com", "password123");
        loginRequest = new LoginRequest("test@example.com", "password123");
    }

    @Test
    @DisplayName("Should register user successfully")
    void register_Success() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        User result = userService.register(registerRequest);

        assertNotNull(result);
        assertEquals("Test User", result.getName());
        assertEquals("test@example.com", result.getEmail());
        verify(userRepository).findByEmail("test@example.com");
        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    @DisplayName("Should throw UserAlreadyExistsException when user exists")
    void register_UserAlreadyExists() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userEntity));

        assertThrows(UserAlreadyExistsException.class, () -> userService.register(registerRequest));
        verify(userRepository).findByEmail("test@example.com");
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    @DisplayName("Should throw ServiceException when repository fails")
    void register_RepositoryException() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(userRepository.save(any(UserEntity.class)))
                .thenThrow(new RuntimeException("Database error"));

        assertThrows(ServiceException.class, () -> userService.register(registerRequest));
    }

    @Test
    @DisplayName("Should sign in user successfully")
    void signIn_Success() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        User result = userService.signIn(loginRequest);

        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("Test User", result.getName());
        assertEquals("test@example.com", result.getEmail());
        verify(userRepository).findByEmail("test@example.com");
        verify(passwordEncoder).matches("password123", "hashedPassword");
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when user not found")
    void signIn_UserNotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.signIn(loginRequest));
        verify(userRepository).findByEmail("test@example.com");
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    @DisplayName("Should throw ServiceException when password is invalid")
    void signIn_InvalidPassword() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThrows(ServiceException.class, () -> userService.signIn(loginRequest));
        verify(userRepository).findByEmail("test@example.com");
        verify(passwordEncoder).matches("password123", "hashedPassword");
    }

    @Test
    @DisplayName("Should find user by email successfully")
    void findByEmail_Success() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userEntity));

        User result = userService.findByEmail("test@example.com");

        assertNotNull(result);
        assertEquals("Test User", result.getName());
        assertEquals("test@example.com", result.getEmail());
        verify(userRepository).findByEmail("test@example.com");
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when user not found by email")
    void findByEmail_UserNotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.findByEmail("test@example.com"));
        verify(userRepository).findByEmail("test@example.com");
    }

    @Test
    @DisplayName("Should throw ServiceException when repository fails during findByEmail")
    void findByEmail_RepositoryException() {
        when(userRepository.findByEmail(anyString()))
                .thenThrow(new RuntimeException("Database error"));

        assertThrows(ServiceException.class, () -> userService.findByEmail("test@example.com"));
    }
}
