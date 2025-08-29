package com.startnow.blog.controller_tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.startnow.blog.controller.UserAuthController;
import com.startnow.blog.exception.ResourceNotFoundException;
import com.startnow.blog.exception.ServiceException;
import com.startnow.blog.exception.UserAlreadyExistsException;
import com.startnow.blog.exception_handler.GlobalExceptionHandler;
import com.startnow.blog.model.user_model.LoginRequest;
import com.startnow.blog.model.user_model.RegisterRequest;
import com.startnow.blog.model.user_model.User;
import com.startnow.blog.service.UserServiceInterface;
import com.startnow.blog.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserAuthControllerTests {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @InjectMocks
    private UserAuthController userAuthController;

    @Mock
    private UserServiceInterface userService;

    @Mock
    private JwtUtil jwtUtil;

    private User testUser;
    private LoginRequest loginRequest;
    private RegisterRequest registerRequest;

    @BeforeEach
    void setUp() {
        testUser = User.builder().id("1").name("Test User").email("test@example.com").build();

        loginRequest = new LoginRequest("test@example.com", "password123");
        registerRequest = new RegisterRequest("Test User", "test@example.com", "password123");

        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(userAuthController)
                .setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    void signIn_Success() throws Exception {
        when(userService.signIn(any(LoginRequest.class))).thenReturn(testUser);
        when(jwtUtil.generateToken(anyString())).thenReturn("test-jwt-token");

        mockMvc.perform(post("/api/auth/sign-in").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest))).andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login successful"))
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(cookie().value("token", "test-jwt-token"));

        verify(userService).signIn(any(LoginRequest.class));
        verify(jwtUtil).generateToken("test@example.com");
    }

    @Test
    void signIn_InvalidCredentials() throws Exception {
        when(userService.signIn(any(LoginRequest.class)))
                .thenThrow(new ResourceNotFoundException("User not found"));

        mockMvc.perform(post("/api/auth/sign-in").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void register_Success() throws Exception {
        when(userService.register(any(RegisterRequest.class))).thenReturn(testUser);
        when(jwtUtil.generateToken(anyString())).thenReturn("test-jwt-token");

        mockMvc.perform(post("/api/auth/register").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Registration successful"))
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(cookie().value("token", "test-jwt-token"));

        verify(userService).register(any(RegisterRequest.class));
        verify(jwtUtil).generateToken("test@example.com");
    }

    @Test
    void register_UserAlreadyExists() throws Exception {
        when(userService.register(any(RegisterRequest.class)))
                .thenThrow(new UserAlreadyExistsException("User already exists"));

        mockMvc.perform(post("/api/auth/register").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isConflict());
    }

    @Test
    void logout_Success() throws Exception {
        mockMvc.perform(post("/api/auth/logout")).andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Logged out successfully"))
                .andExpect(cookie().maxAge("token", 0));
    }

    @Test
    void getCurrentUser_Success() throws Exception {
        when(jwtUtil.validateToken("valid-token")).thenReturn(true);
        when(jwtUtil.extractUsername("valid-token")).thenReturn("test@example.com");
        when(userService.findByEmail("test@example.com")).thenReturn(testUser);

        mockMvc.perform(get("/api/auth/me").cookie(new Cookie("token", "valid-token")))
                .andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void getCurrentUser_InvalidToken() throws Exception {
        when(jwtUtil.validateToken("invalid-token")).thenReturn(false);

        mockMvc.perform(get("/api/auth/me").cookie(new Cookie("token", "invalid-token")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getCurrentUser_NoToken() throws Exception {
        mockMvc.perform(get("/api/auth/me")).andExpect(status().isUnauthorized());
    }
}
