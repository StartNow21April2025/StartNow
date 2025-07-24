package com.startnow.blog.service;

import com.startnow.blog.entity.ArticleContentEntity;
import com.startnow.blog.entity.ArticleEntity;
import com.startnow.blog.exception.ServiceException;
import com.startnow.blog.model.Article;
import com.startnow.blog.model.ArticleContent;
import com.startnow.blog.repository.IArticleContentRepository;
import com.startnow.blog.repository.IArticleRepository;
import com.startnow.blog.util.ArticleUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArticleService implements ArticleServiceInterface {

    private static final Logger logger = LoggerFactory.getLogger(ArticleService.class);

    private final IArticleRepository articleRepository;
    private final IArticleContentRepository articleContentRepository;

    /**
     * Retrieves all articles with summary information.
     */
    @Override
    public List<Article> getAllArticles() {
        try {
            List<ArticleEntity> articleEntityList = articleRepository.findAll();
            if (articleEntityList.isEmpty()) {
                logger.warn("Articles not found in the database.");
                return new ArrayList<>();
            }
            logger.info("Fetched {} articles from table {}", articleEntityList.size(), "Articles");
            return articleEntityList.stream().filter(Objects::nonNull)
                    .map(ArticleUtil::convertToArticle).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching all Articles: {}", e.getMessage(), e);
            throw new ServiceException("Failed to fetch Articles", e);
        }
    }

    /**
     * Retrieves a full article by its slug.
     */
    @Override
    public ArticleContent getArticleContentBySlug(String slug) {
        try {
            Optional<ArticleContentEntity> articleContentEntity =
                    articleContentRepository.findById(slug);
            if (articleContentEntity.isEmpty()) {
                logger.warn("Article not found for slug: {}", slug);
                return null;
            }
            logger.info("Fetched article for slug {} from table {}", slug, "ArticleContent");
            return ArticleUtil.convertToArticleContent(articleContentEntity.get());
        } catch (Exception e) {
            log.error("Error fetching ArticleContent: {}", e.getMessage(), e);
            throw new ServiceException("Failed to fetch Article", e);
        }
    }

    /**
     * Retrieves articles for list of id with summary information.
     */
    @Override
    public List<Article> getArticlesByIds(List<Integer> ids) {
        try {
            List<ArticleEntity> articleEntityList = articleRepository.findByIds(ids);
            if (articleEntityList.isEmpty()) {
                logger.warn("Articles not found for list of ids {} in the database.", ids);
                return new ArrayList<>();
            }
            logger.info("Fetched {} articles for list of ids {} from table {}",
                    articleEntityList.size(), ids, "Articles");
            return articleEntityList.stream().filter(Objects::nonNull)
                    .map(ArticleUtil::convertToArticle).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching all Articles: {}", e.getMessage(), e);
            throw new ServiceException("Failed to fetch Articles", e);
        }
    }
}
