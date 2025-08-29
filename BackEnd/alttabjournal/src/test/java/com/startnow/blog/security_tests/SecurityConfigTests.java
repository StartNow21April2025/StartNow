package com.startnow.blog.security_tests;

import com.startnow.blog.configuration.JwtAuthenticationFilter;
import com.startnow.blog.configuration.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.cors.CorsConfigurationSource;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SecurityConfigTests {

    private SecurityConfig securityConfig;

    @Mock
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setUp() {
        securityConfig = new SecurityConfig(jwtAuthenticationFilter);
    }

    @Test
    @DisplayName("Should create SecurityConfig with JwtAuthenticationFilter")
    void constructor_Success() {
        assertNotNull(securityConfig);
    }

    @Test
    @DisplayName("Should create CORS configuration source")
    void corsConfigurationSource_Success() {
        CorsConfigurationSource corsSource = securityConfig.corsConfigurationSource();

        assertNotNull(corsSource);
    }

    @Test
    @DisplayName("Should handle null JwtAuthenticationFilter gracefully")
    void constructor_NullFilter() {
        assertDoesNotThrow(() -> new SecurityConfig(null));
    }
}
