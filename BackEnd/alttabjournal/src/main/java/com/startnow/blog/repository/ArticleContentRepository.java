package com.startnow.blog.repository;

import com.startnow.blog.entity.ArticleContentEntity;
import com.startnow.blog.exception.RepositoryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import java.util.Optional;

@Repository
@Slf4j
public class ArticleContentRepository implements IArticleContentRepository {
    private final DynamoDbTable<ArticleContentEntity> contentTable;

    public ArticleContentRepository(DynamoDbTable<ArticleContentEntity> contentTable) {
        this.contentTable = contentTable;
    }

    /**
     * Saves the article fullContent entity to the DynamoDB table.
     *
     * @param content The article fullContent entity to save.
     * @return The saved article fullContent entity.
     */
    @Override
    public ArticleContentEntity save(ArticleContentEntity content) {
        try {
            contentTable.putItem(content);
            return content;
        } catch (Exception e) {
            log.error("Error saving article fullContent: {}", e.getMessage());
            throw new RepositoryException("Failed to save article fullContent", e);
        }
    }

    /**
     * Finds an article fullContent entity by its ID.
     *
     * @param slug The ID of the article fullContent entity to find.
     * @return An Optional containing the found article fullContent entity, or empty if not found.
     */
    @Override
    public Optional<ArticleContentEntity> findById(String slug) {
        try {
            return Optional
                    .ofNullable(contentTable.getItem(Key.builder().partitionValue(slug).build()));
        } catch (Exception e) {
            log.error("Error finding article fullContent: {}", e.getMessage());
            throw new RepositoryException("Failed to find article fullContent", e);
        }
    }

    /**
     * Deletes an article fullContent entity by its ID.
     *
     * @param slug The ID of the article fullContent entity to delete.
     */
    @Override
    public void delete(String slug) {
        try {
            contentTable.deleteItem(Key.builder().partitionValue(slug).build());
        } catch (Exception e) {
            log.error("Error deleting article fullContent: {}", e.getMessage());
            throw new RepositoryException("Failed to delete article fullContent", e);
        }
    }
}
