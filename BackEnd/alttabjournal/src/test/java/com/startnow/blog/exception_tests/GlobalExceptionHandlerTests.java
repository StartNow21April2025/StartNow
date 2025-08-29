package com.startnow.blog.exception_tests;

import com.startnow.blog.exception.*;
import com.startnow.blog.exception_handler.GlobalExceptionHandler;
import com.startnow.blog.model.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTests {

    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private WebRequest webRequest;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    @DisplayName("Should handle ResourceNotFoundException")
    void handleResourceNotFoundException() {
        ResourceNotFoundException exception = new ResourceNotFoundException("Resource not found");

        ResponseEntity<ErrorResponse> response =
                globalExceptionHandler.handleResourceNotFoundException(exception, webRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(404, response.getBody().getStatus());
        assertEquals("Resource not found", response.getBody().getMessage());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    @DisplayName("Should handle UserAlreadyExistsException")
    void handleUserAlreadyExistsException() {
        UserAlreadyExistsException exception =
                new UserAlreadyExistsException("User already exists");

        ResponseEntity<?> response = globalExceptionHandler.handleUserAlreadyExists(exception);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertEquals(409, errorResponse.getStatus());
        assertEquals("User already exists", errorResponse.getMessage());
        assertNotNull(errorResponse.getTimestamp());
    }

    @Test
    @DisplayName("Should handle ServiceException")
    void handleServiceException() {
        ServiceException exception = new ServiceException("Service error occurred");

        ResponseEntity<Object> response = globalExceptionHandler.handleServiceException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertEquals(500, errorResponse.getStatus());
        assertEquals("Service error occurred", errorResponse.getMessage());
        assertNotNull(errorResponse.getTimestamp());
    }

    @Test
    @DisplayName("Should handle RuntimeException")
    void handleRuntimeException() {
        RuntimeException exception = new RuntimeException("Runtime error occurred");

        ResponseEntity<Object> response = globalExceptionHandler.handleRuntimeException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertEquals(500, errorResponse.getStatus());
        assertEquals("Runtime error occurred", errorResponse.getMessage());
        assertNotNull(errorResponse.getTimestamp());
    }
}
