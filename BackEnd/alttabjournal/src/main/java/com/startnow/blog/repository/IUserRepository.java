package com.startnow.blog.repository;

import com.startnow.blog.entity.UserEntity;

import java.util.Optional;

// User Repository Interface
public interface IUserRepository extends BaseRepository<UserEntity, String> {

    public UserEntity save(UserEntity user);

    public void delete(String id);

    public Optional<UserEntity> findById(String id);

    public Optional<UserEntity> findByEmail(String email);

    public UserEntity update(UserEntity user);
}
