package com.startnow.blog.repository_tests;

import com.startnow.blog.entity.ArticleEntity;
import com.startnow.blog.exception.RepositoryException;
import com.startnow.blog.repository.ArticleRepository;
import com.startnow.blog.repository.IArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.DeleteItemEnhancedResponse;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticleRepositoryTests {

    @Mock
    private DynamoDbTable<ArticleEntity> articleTable;

    @Mock
    private PageIterable<ArticleEntity> pageIterable;

    private IArticleRepository articleRepository;

    @BeforeEach
    void setUp() {
        articleRepository = new ArticleRepository(articleTable);
    }

    @Test
    void whenSaveArticle_thenReturnSavedArticle() {
        // Arrange
        ArticleEntity article = createSampleArticle();
        doNothing().when(articleTable).putItem(any(ArticleEntity.class));

        // Act
        ArticleEntity savedArticle = articleRepository.save(article);

        // Assert
        assertNotNull(savedArticle);
        assertEquals(article.getTitleId(), savedArticle.getTitleId());
        assertEquals(article.getTitle(), savedArticle.getTitle());
        verify(articleTable).putItem(article);
    }

    @Test
    void whenSaveArticleAndExceptionOccurs_thenThrowRepositoryException() {
        // Arrange
        ArticleEntity article = createSampleArticle();
        doThrow(new RuntimeException("DynamoDB error")).when(articleTable)
                .putItem(any(ArticleEntity.class));

        // Act & Assert
        RepositoryException exception =
                assertThrows(RepositoryException.class, () -> articleRepository.save(article));
        assertEquals("Failed to save article", exception.getMessage());
    }

    @Test
    void whenFindByIdAndArticleExists_thenReturnArticle() {
        // Arrange
        ArticleEntity article = createSampleArticle();
        when(articleTable.getItem(any(Key.class))).thenReturn(article);

        // Act
        Optional<ArticleEntity> result = articleRepository.findById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(article.getTitleId(), result.get().getTitleId());
        assertEquals(article.getTitle(), result.get().getTitle());
    }

    @Test
    void whenFindByIdAndArticleDoesNotExist_thenReturnEmptyOptional() {
        // Arrange
        when(articleTable.getItem(any(Key.class))).thenReturn(null);

        // Act
        Optional<ArticleEntity> result = articleRepository.findById(1);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void whenFindByIdAndExceptionOccurs_thenThrowRepositoryException() {
        // Arrange
        when(articleTable.getItem(any(Key.class)))
                .thenThrow(new RuntimeException("DynamoDB error"));

        // Act & Assert
        RepositoryException exception =
                assertThrows(RepositoryException.class, () -> articleRepository.findById(1));
        assertEquals("Failed to find article", exception.getMessage());
    }

    @Test
    void whenDeleteArticle_thenSuccessfullyDelete() {
        // Arrange
        when(articleTable.deleteItem(any(Key.class))).thenReturn(
                DeleteItemEnhancedResponse.builder(ArticleEntity.class).build().attributes());

        // Act & Assert
        assertDoesNotThrow(() -> articleRepository.delete(1));
        verify(articleTable).deleteItem(any(Key.class));
    }

    @Test
    void whenDeleteArticleAndExceptionOccurs_thenThrowRepositoryException() {
        // Arrange
        doThrow(new RuntimeException("DynamoDB error")).when(articleTable)
                .deleteItem(any(Key.class));

        // Act & Assert
        RepositoryException exception =
                assertThrows(RepositoryException.class, () -> articleRepository.delete(1));
        assertEquals("Failed to delete article", exception.getMessage());
    }

    @Test
    void whenFindAll_thenReturnAllArticles() {
        // Arrange
        List<ArticleEntity> articles = createSampleArticleList();
        when(articleTable.scan()).thenReturn(pageIterable);
        when(pageIterable.items()).thenReturn(() -> articles.iterator());

        // Act
        List<ArticleEntity> result = articleRepository.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(articles.get(0).getTitleId(), result.get(0).getTitleId());
        assertEquals(articles.get(1).getTitleId(), result.get(1).getTitleId());
        verify(articleTable).scan();
    }

    @Test
    void whenFindAllAndExceptionOccurs_thenThrowRepositoryException() {
        // Arrange
        when(articleTable.scan()).thenThrow(new RuntimeException("DynamoDB error"));

        // Act & Assert
        RepositoryException exception =
                assertThrows(RepositoryException.class, () -> articleRepository.findAll());
        assertEquals("Failed to find all Articles", exception.getMessage());
    }

    private ArticleEntity createSampleArticle() {
        return ArticleEntity.builder().titleId(1).title("Test Article").slug("test-article")
                .description("Test Description").authorName("Test Author").createdAt("2024-01-01")
                .updatedAt("2024-01-01").build();
    }

    private List<ArticleEntity> createSampleArticleList() {
        return List.of(
                ArticleEntity.builder().titleId(1).title("Test Article 1").slug("test-article-1")
                        .description("Test Description 1").authorName("Test Author")
                        .createdAt("2024-01-01").updatedAt("2024-01-01").build(),
                ArticleEntity.builder().titleId(2).title("Test Article 2").slug("test-article-2")
                        .description("Test Description 2").authorName("Test Author")
                        .createdAt("2024-01-01").updatedAt("2024-01-01").build());
    }
}


