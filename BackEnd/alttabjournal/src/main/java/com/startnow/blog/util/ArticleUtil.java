package com.startnow.blog.util;

import com.startnow.blog.entity.ArticleContentEntity;
import com.startnow.blog.entity.ArticleEntity;
import com.startnow.blog.model.Article;
import com.startnow.blog.model.ArticleContent;
import com.startnow.blog.model.HomePageSectionContent;

public final class ArticleUtil {
    // Private constructor to prevent instantiation
    private ArticleUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static Article convertToArticle(ArticleEntity entity) {
        return Article.builder().articleId(entity.getTitleId()).title(entity.getTitle())
                .description(entity.getDescription()).slug(entity.getSlug()).build();
    }

    public static HomePageSectionContent convertToHomePageSectionContent(ArticleEntity entity) {
        return HomePageSectionContent.builder().title(entity.getTitle()).tag(entity.getTag())
                .slug(entity.getSlug()).date(entity.getUpdatedAt()).author(entity.getAuthorName())
                .description(entity.getDescription()).imageUrl(entity.getImageUrl()).build();
    }

    public static ArticleContent convertToArticleContent(
            ArticleContentEntity articleContentEntity) {
        return ArticleContent.builder().slug(articleContentEntity.getSlug())
                .title(articleContentEntity.getTitle()).author(articleContentEntity.getAuthor())
                .date(articleContentEntity.getDate()).sections(articleContentEntity.getSections())
                .status(articleContentEntity.getStatus()).build();
    }
}
