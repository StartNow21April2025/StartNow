package com.startnow.blog.repository_tests;

import com.startnow.blog.entity.ArticleContentEntity;
import com.startnow.blog.exception.RepositoryException;
import com.startnow.blog.repository.ArticleContentRepository;
import com.startnow.blog.repository.IArticleContentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.DeleteItemEnhancedResponse;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticleContentRepositoryTests {

    @Mock
    private DynamoDbTable<ArticleContentEntity> articleContentTable;

    private IArticleContentRepository articleContentRepository;

    private static final String slug = "test-slug";
    private static final String CONTENT = "Test Content";

    @BeforeEach
    void setUp() {
        articleContentRepository = new ArticleContentRepository(articleContentTable);
    }

    @Test
    void whenSaveArticleContent_thenReturnSavedArticleContent() {
        // Arrange
        ArticleContentEntity articleContent = createSampleArticleContent();
        doNothing().when(articleContentTable).putItem(any(ArticleContentEntity.class));

        // Act
        ArticleContentEntity savedArticleContent = articleContentRepository.save(articleContent);

        // Assert
        assertNotNull(savedArticleContent);
        assertEquals(slug, savedArticleContent.getSlug());
        assertEquals(CONTENT, savedArticleContent.getTitle());
        verify(articleContentTable).putItem(articleContent);
    }

    @Test
    void whenSaveArticleContentAndExceptionOccurs_thenThrowRepositoryException() {
        // Arrange
        ArticleContentEntity articleContent = createSampleArticleContent();
        doThrow(new RuntimeException("DynamoDB error")).when(articleContentTable)
                .putItem(any(ArticleContentEntity.class));

        // Act & Assert
        RepositoryException exception = assertThrows(RepositoryException.class,
                () -> articleContentRepository.save(articleContent));
        assertEquals("Failed to save article fullContent", exception.getMessage());
    }

    @Test
    void whenFindByIdAndArticleContentExists_thenReturnArticleContent() {
        // Arrange
        ArticleContentEntity articleContent = createSampleArticleContent();
        when(articleContentTable.getItem(any(Key.class))).thenReturn(articleContent);

        // Act
        Optional<ArticleContentEntity> result = articleContentRepository.findById(slug);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(slug, result.get().getSlug());
        assertEquals(CONTENT, result.get().getTitle());
    }

    @Test
    void whenFindByIdAndArticleContentDoesNotExist_thenReturnEmptyOptional() {
        // Arrange
        when(articleContentTable.getItem(any(Key.class))).thenReturn(null);

        // Act
        Optional<ArticleContentEntity> result = articleContentRepository.findById(slug);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void whenFindByIdAndExceptionOccurs_thenThrowRepositoryException() {
        // Arrange
        doThrow(new RuntimeException("DynamoDB error")).when(articleContentTable)
                .getItem(any(Key.class));

        // Act & Assert
        RepositoryException exception = assertThrows(RepositoryException.class,
                () -> articleContentRepository.findById(slug));
        assertEquals("Failed to find article fullContent", exception.getMessage());
    }

    @Test
    void whenDeleteArticleContent_thenSuccessfullyDelete() {
        // Arrange
        when(articleContentTable.deleteItem(Key.builder().partitionValue(slug).build()))
                .thenReturn(DeleteItemEnhancedResponse.builder(ArticleContentEntity.class).build()
                        .attributes());

        // Act & Assert
        assertDoesNotThrow(() -> articleContentRepository.delete(slug));
        verify(articleContentTable).deleteItem(any(Key.class));
    }

    @Test
    void whenDeleteArticleContentAndExceptionOccurs_thenThrowRepositoryException() {
        // Arrange
        doThrow(new RuntimeException("DynamoDB error")).when(articleContentTable)
                .deleteItem(any(Key.class));

        // Act & Assert
        RepositoryException exception = assertThrows(RepositoryException.class,
                () -> articleContentRepository.delete(slug));
        assertEquals("Failed to delete article fullContent", exception.getMessage());
    }

    private ArticleContentEntity createSampleArticleContent() {
        return ArticleContentEntity.builder().slug(slug).title(CONTENT).build();
    }
}

