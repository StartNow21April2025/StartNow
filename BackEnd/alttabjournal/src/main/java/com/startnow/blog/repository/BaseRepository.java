package com.startnow.blog.repository;

import java.util.Optional;

public interface BaseRepository<T, ID> {
    T save(T entity);

    Optional<T> findById(ID id);

    void delete(ID id);
}
