package com.startnow.blog.util;

import com.startnow.blog.entity.ArticleContentEntity;
import com.startnow.blog.entity.ArticleEntity;
import com.startnow.blog.model.Article;
import com.startnow.blog.model.ArticleContent;

public final class ArticleUtil {
    // Private constructor to prevent instantiation
    private ArticleUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static Article convertToArticle(ArticleEntity entity) {
        return Article.builder().articleId(entity.getTitleId()).title(entity.getTitle())
                .description(entity.getDescription()).slug(entity.getSlug()).build();
    }

    public static ArticleContent convertToArticleContent(ArticleContentEntity entity) {
        return ArticleContent.builder().slug(entity.getSlug()).fullContent(entity.getFullContent())
                .status(entity.getStatus()).authorName(entity.getAuthorName())
                .createdAt(entity.getCreatedAt()).updatedAt(entity.getUpdatedAt()).build();
    }
}
