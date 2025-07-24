package com.startnow.blog.service;

import com.startnow.blog.model.user_model.LoginRequest;
import com.startnow.blog.model.user_model.RegisterRequest;
import com.startnow.blog.model.user_model.User;

public interface UserServiceInterface {

    public User signIn(LoginRequest loginRequest);

    public User register(RegisterRequest registerRequest);

    public User findByEmail(String email);
}
