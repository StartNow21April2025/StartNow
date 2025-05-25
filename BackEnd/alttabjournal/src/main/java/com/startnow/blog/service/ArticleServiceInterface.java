package com.startnow.blog.service;

import com.startnow.blog.model.Article;
import com.startnow.blog.model.ArticleContent;

import java.util.List;

public interface ArticleServiceInterface {

    public List<Article> getAllArticles();

    public ArticleContent getArticleContentBySlug(String slug);

}
