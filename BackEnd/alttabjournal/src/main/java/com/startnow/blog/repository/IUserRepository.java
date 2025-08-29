package com.startnow.blog.repository;

import com.startnow.blog.entity.UserEntity;

import java.util.Optional;

// User Repository Interface
public interface IUserRepository extends BaseRepository<UserEntity, String> {

    public Optional<UserEntity> findByEmail(String email);

    public UserEntity update(UserEntity user);
}
