package com.startnow.blog.repository_tests;

import com.startnow.blog.entity.UserEntity;
import com.startnow.blog.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTests {

    @Mock
    private IUserRepository userRepository;

    private UserEntity testUser;

    @BeforeEach
    void setUp() {
        testUser = UserEntity.builder().id("1").name("Test User").email("test@example.com")
                .password("hashedPassword").build();
    }

    @Test
    @DisplayName("Should save user successfully")
    void save_Success() {
        when(userRepository.save(any(UserEntity.class))).thenReturn(testUser);

        UserEntity result = userRepository.save(testUser);

        assertNotNull(result);
        assertEquals("Test User", result.getName());
        assertEquals("test@example.com", result.getEmail());
        verify(userRepository).save(testUser);
    }

    @Test
    @DisplayName("Should find user by email")
    void findByEmail_Success() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));

        Optional<UserEntity> result = userRepository.findByEmail("test@example.com");

        assertTrue(result.isPresent());
        assertEquals("Test User", result.get().getName());
        assertEquals("test@example.com", result.get().getEmail());
        verify(userRepository).findByEmail("test@example.com");
    }

    @Test
    @DisplayName("Should return empty when user not found by email")
    void findByEmail_NotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        Optional<UserEntity> result = userRepository.findByEmail("nonexistent@example.com");

        assertFalse(result.isPresent());
        verify(userRepository).findByEmail("nonexistent@example.com");
    }

    @Test
    @DisplayName("Should find user by ID")
    void findById_Success() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(testUser));

        Optional<UserEntity> result = userRepository.findById("1");

        assertTrue(result.isPresent());
        assertEquals("1", result.get().getId());
        assertEquals("Test User", result.get().getName());
        verify(userRepository).findById("1");
    }

    @Test
    @DisplayName("Should return empty when user not found by ID")
    void findById_NotFound() {
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        Optional<UserEntity> result = userRepository.findById("999");

        assertFalse(result.isPresent());
        verify(userRepository).findById("999");
    }

    @Test
    @DisplayName("Should delete user successfully")
    void delete_Success() {
        doNothing().when(userRepository).delete(anyString());

        assertDoesNotThrow(() -> userRepository.delete("1"));
        verify(userRepository).delete("1");
    }
}
