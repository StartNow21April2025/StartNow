package com.startnow.blog.util_tests;

import com.startnow.blog.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtUtilTests {

    private JwtUtil jwtUtil;
    private final String testUsername = "test@example.com";

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
    }

    @Test
    @DisplayName("Should generate valid JWT token")
    void generateToken_Success() {
        String token = jwtUtil.generateToken(testUsername);

        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.contains("."));
    }

    @Test
    @DisplayName("Should extract username from valid token")
    void extractUsername_Success() {
        String token = jwtUtil.generateToken(testUsername);

        String extractedUsername = jwtUtil.extractUsername(token);

        assertEquals(testUsername, extractedUsername);
    }

    @Test
    @DisplayName("Should validate valid token")
    void validateToken_ValidToken() {
        String token = jwtUtil.generateToken(testUsername);

        boolean isValid = jwtUtil.validateToken(token);

        assertTrue(isValid);
    }

    @Test
    @DisplayName("Should reject invalid token")
    void validateToken_InvalidToken() {
        String invalidToken = "invalid.token.here";

        boolean isValid = jwtUtil.validateToken(invalidToken);

        assertFalse(isValid);
    }

    @Test
    @DisplayName("Should reject null token")
    void validateToken_NullToken() {
        boolean isValid = jwtUtil.validateToken(null);

        assertFalse(isValid);
    }

    @Test
    @DisplayName("Should validate token with username")
    void validateTokenWithUsername_ValidToken() {
        String token = jwtUtil.generateToken(testUsername);

        boolean isValid = jwtUtil.validateToken(token, testUsername);

        assertTrue(isValid);
    }

    @Test
    @DisplayName("Should reject token with wrong username")
    void validateTokenWithUsername_WrongUsername() {
        String token = jwtUtil.generateToken(testUsername);

        boolean isValid = jwtUtil.validateToken(token, "wrong@example.com");

        assertFalse(isValid);
    }

    @Test
    @DisplayName("Should handle malformed token gracefully")
    void extractUsername_MalformedToken() {
        String malformedToken = "malformed.token";

        assertThrows(JwtException.class, () -> jwtUtil.extractUsername(malformedToken));
    }
}
