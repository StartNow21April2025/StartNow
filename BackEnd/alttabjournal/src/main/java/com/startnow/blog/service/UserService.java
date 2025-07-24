package com.startnow.blog.service;

import com.startnow.blog.entity.UserEntity;
import com.startnow.blog.exception.ResourceNotFoundException;
import com.startnow.blog.exception.ServiceException;
import com.startnow.blog.exception.UserAlreadyExistsException;
import com.startnow.blog.model.user_model.LoginRequest;
import com.startnow.blog.model.user_model.RegisterRequest;
import com.startnow.blog.model.user_model.User;
import com.startnow.blog.repository.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService implements UserServiceInterface {

    private final IUserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(IUserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(RegisterRequest registerRequest) {
        try {
            if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
                throw new UserAlreadyExistsException("User already exists");
            }
            String hashedPassword = passwordEncoder.encode(registerRequest.getPassword());
            UserEntity userEntity = userRepository.save(new UserEntity(registerRequest.getName(),
                    registerRequest.getEmail(), hashedPassword));
            return User.builder().name(userEntity.getName()).email(userEntity.getEmail()).build();
        } catch (UserAlreadyExistsException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error registering user: {}", e.getMessage());
            throw new ServiceException("Failed to register user", e);
        }
    }

    @Override
    public User signIn(LoginRequest loginRequest) {
        try {
            Optional<UserEntity> userEntity = userRepository.findByEmail(loginRequest.getEmail());
            if (userEntity.isEmpty()) {
                throw new ResourceNotFoundException(
                        "User not found with LoginRequest: " + loginRequest.getEmail());
            } else if (!passwordEncoder.matches(loginRequest.getPassword(),
                    userEntity.get().getPassword())) {
                throw new ServiceException("Invalid password");
            } else {
                log.info("User with email {} signed in successfully", loginRequest.getEmail());
                return User.builder().id(userEntity.get().getId()).name(userEntity.get().getName())
                        .email(userEntity.get().getEmail()).build();
            }
        } catch (ResourceNotFoundException | ServiceException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error Signing in User with email {}: {}", loginRequest.getEmail(),
                    e.getMessage());
            throw new ServiceException("Failed to sign in User", e);
        }
    }

    @Override
    public User findByEmail(String email){
        try {
            Optional<UserEntity> userEntity = userRepository.findByEmail(email);
            if(userEntity.isEmpty()){
                throw new ResourceNotFoundException(
                        "User not found with LoginRequest: " + email);
            } else {
                return User.builder().name(userEntity.get().getName()).email(userEntity.get().getEmail()).build();
            }
        } catch(ResourceNotFoundException e){
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Failed to fetch User", e);
        }
    }
}
