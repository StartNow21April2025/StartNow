package com.startnow.blog.controller;

import com.startnow.blog.model.user_model.LoginRequest;
import com.startnow.blog.model.user_model.RegisterRequest;
import com.startnow.blog.model.user_model.User;
import com.startnow.blog.service.UserServiceInterface;
import com.startnow.blog.util.JwtUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/** REST controller for User Authorization. */
@RestController
@RequestMapping("/api")
@Tag(name = "User Authorization Controller", description = "APIs for managing Article")
public class UserAuthController {
    private final UserServiceInterface userService;
    private final JwtUtil jwtUtil;

    public UserAuthController(UserServiceInterface userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    /**
     *
     * @param loginRequest which consists of email and password
     *
     */
    @PostMapping("/auth/sign-in")
    public ResponseEntity<?> signIn(@RequestBody LoginRequest loginRequest,
            HttpServletResponse response) {
        User user = userService.signIn(loginRequest); // You validate username/password here
        String token = jwtUtil.generateToken(user.getEmail());

        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // set to true if using HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60); // 1 day

        response.addCookie(cookie);

        return ResponseEntity.ok(Map.of("message", "Login successful", "name", user.getName(),
                "email", user.getEmail()));
    }


    /**
     *
     * @param registerRequest which consists of name, email and password
     *
     */
    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest,
            HttpServletResponse response) {
        User user = userService.register(registerRequest);
        String token = jwtUtil.generateToken(user.getEmail());

        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // set to true if using HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60); // 1 day

        response.addCookie(cookie);

        return ResponseEntity.ok(Map.of("message", "Registration successful", "name",
                user.getName(), "email", user.getEmail()));
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // delete the cookie

        response.addCookie(cookie);
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }

    @GetMapping("/auth/me")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
        String token = extractTokenFromCookie(request);
        if (token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        String email = jwtUtil.extractUsername(token);
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(user);
    }

    private String extractTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
