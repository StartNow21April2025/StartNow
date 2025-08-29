package com.startnow.blog.repository;

import com.startnow.blog.entity.SectionArticleMappingEntity;
import com.startnow.blog.exception.RepositoryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.util.List;

@Repository
@Slf4j
public class SectionArticleMappingRepository implements ISectionArticleMappingRepository {
    private final DynamoDbTable<SectionArticleMappingEntity> sectionArticleMappingTable;

    public SectionArticleMappingRepository(
            DynamoDbTable<SectionArticleMappingEntity> sectionArticleMappingTable) {
        this.sectionArticleMappingTable = sectionArticleMappingTable;
    }

    /**
     * Save an article to the DynamoDB table.
     *
     * @param article The article entity to save.
     * @return The saved article entity.
     */
    @Override
    public SectionArticleMappingEntity save(SectionArticleMappingEntity article) {
        try {
            sectionArticleMappingTable.putItem(article);
            return article;
        } catch (Exception e) {
            log.error("Error saving article: {}", e.getMessage());
            throw new RepositoryException("Failed to save article", e);
        }
    }


    /**
     * Find an article by its ID.
     *
     * @param sectionId The ID of the section to find.
     * @return List containing the sectionArticleMapping entity
     */
    @Override
    public List<SectionArticleMappingEntity> findBySectionId(String sectionId) {
        try {
            return sectionArticleMappingTable
                    .query(r -> r.queryConditional(QueryConditional
                            .keyEqualTo(Key.builder().partitionValue(sectionId).build())))
                    .items().stream().toList();
        } catch (Exception e) {
            log.error("Error finding articleList by id: {}", e.getMessage());
            throw new RepositoryException("Failed to find articleList of section", e);
        }
    }

    /**
     * Delete an section by its ID.
     *
     * @param id The ID of the section to delete.
     */
    @Override
    public void delete(String id) {
        try {
            sectionArticleMappingTable.deleteItem(Key.builder().partitionValue(id).build());
        } catch (Exception e) {
            log.error("Error deleting section: {}", e.getMessage());
            throw new RepositoryException("Failed to delete section", e);
        }
    }

    /**
     * Delete an section by its sectionID and ArticleID.
     *
     * @param sectionId The sectionID of the article to delete.
     * @param articleId The articleId of the article to delete.
     */
    @Override
    public void deleteById(String sectionId, String articleId) {
        try {
            sectionArticleMappingTable.deleteItem(
                    Key.builder().partitionValue(sectionId).sortValue(articleId).build());
        } catch (Exception e) {
            log.error("Error deleting article of section: {}", e.getMessage());
            throw new RepositoryException("Failed to delete article", e);
        }
    }
}
