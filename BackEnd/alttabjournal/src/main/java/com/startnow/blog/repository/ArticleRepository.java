package com.startnow.blog.repository;

import com.startnow.blog.entity.ArticleEntity;
import com.startnow.blog.exception.RepositoryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Move table creation and operations to Repository
@Repository
@Slf4j
public class ArticleRepository implements IArticleRepository {
    private final DynamoDbTable<ArticleEntity> articleTable;

    public ArticleRepository(DynamoDbTable<ArticleEntity> articleTable) {
        this.articleTable = articleTable;
    }

    /**
     * Save an article to the DynamoDB table.
     *
     * @param article The article entity to save.
     * @return The saved article entity.
     */
    @Override
    public ArticleEntity save(ArticleEntity article) {
        try {
            articleTable.putItem(article);
            return article;
        } catch (Exception e) {
            log.error("Error saving article: {}", e.getMessage());
            throw new RepositoryException("Failed to save article", e);
        }
    }

    /**
     * Find an article by its ID.
     *
     * @param id The ID of the article to find.
     * @return An Optional containing the article entity if found, or an empty Optional if not
     *         found.
     */
    @Override
    public Optional<ArticleEntity> findById(Integer id) {
        try {
            return Optional
                    .ofNullable(articleTable.getItem(Key.builder().partitionValue(id).build()));
        } catch (Exception e) {
            log.error("Error finding article by id: {}", e.getMessage());
            throw new RepositoryException("Failed to find article", e);
        }
    }

    /**
     * Delete an article by its ID.
     *
     * @param id The ID of the article to delete.
     */
    @Override
    public void delete(Integer id) {
        try {
            articleTable.deleteItem(Key.builder().partitionValue(id).build());
        } catch (Exception e) {
            log.error("Error deleting article: {}", e.getMessage());
            throw new RepositoryException("Failed to delete article", e);
        }
    }

    /**
     * Find all articles in the DynamoDB table.
     *
     * @return A list of all article entities.
     */
    @Override
    public List<ArticleEntity> findAll() {
        try {
            List<ArticleEntity> articles = new ArrayList<>();

            // Debug log each item as it's scanned
            articleTable.scan().items().forEach(articles::add);

            log.info("Found {} articles in DynamoDB", articles.size());
            return articles;
        } catch (Exception e) {
            log.error("Error finding all Articles: {}", e.getMessage());
            throw new RepositoryException("Failed to find all Articles", e);
        }
    }
}
