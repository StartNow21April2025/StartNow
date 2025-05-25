package com.startnow.blog.repository;

import com.startnow.blog.entity.ArticleEntity;

import java.util.List;
import java.util.Optional;

// Article Repository Interface
public interface IArticleRepository extends BaseRepository<ArticleEntity, Integer> {

    public ArticleEntity save(ArticleEntity article);

    public void delete(Integer id);

    public Optional<ArticleEntity> findById(Integer id);

    public List<ArticleEntity> findAll();
}
