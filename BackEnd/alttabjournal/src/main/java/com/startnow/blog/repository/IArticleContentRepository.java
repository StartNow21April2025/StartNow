package com.startnow.blog.repository;

import com.startnow.blog.entity.ArticleContentEntity;

import java.util.Optional;

// Article Content Repository Interface
public interface IArticleContentRepository extends BaseRepository<ArticleContentEntity, String> {

    public ArticleContentEntity save(ArticleContentEntity content);

    public void delete(String slug);

    public Optional<ArticleContentEntity> findById(String slug);
}
