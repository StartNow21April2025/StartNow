package com.startnow.blog.service_tests;

import com.startnow.blog.entity.ArticleContentEntity;
import com.startnow.blog.entity.ArticleEntity;
import com.startnow.blog.exception.ServiceException;
import com.startnow.blog.model.Article;
import com.startnow.blog.model.ArticleContent;
import com.startnow.blog.repository.IArticleContentRepository;
import com.startnow.blog.repository.IArticleRepository;
import com.startnow.blog.service.ArticleService;
import com.startnow.blog.util.ArticleUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTests {

    @InjectMocks
    private ArticleService articleService;

    @Mock
    private IArticleRepository articleRepository;

    @Mock
    private IArticleContentRepository articleContentRepository;

    @Test
    @DisplayName("Should return all articles when articles exist")
    void getAllArticles_ReturnsList() {
        // Mocking the repository to return a list of ArticleEntity objects
        List<ArticleEntity> mockArticleEntities = List.of(
                ArticleEntity.builder().titleId(1).title("Article 1").description("Desc 1")
                        .slug("article-1").build(),
                ArticleEntity.builder().titleId(2).title("Article 2").description("Desc 2")
                        .slug("article-2").build());

        when(articleRepository.findAll()).thenReturn(mockArticleEntities);
        List<Article> articles = articleService.getAllArticles();

        assertAll(() -> assertEquals(2, articles.size()),
                () -> assertEquals("Article 1", articles.get(0).getTitle()),
                () -> assertEquals("Desc 1", articles.get(0).getDescription()),
                () -> assertEquals("article-1", articles.get(0).getSlug()),
                () -> assertEquals("Article 2", articles.get(1).getTitle()),
                () -> assertEquals("Desc 2", articles.get(1).getDescription()),
                () -> assertEquals("article-2", articles.get(1).getSlug()));

        // Verify that the repository and util methods were called correctly
        verify(articleRepository).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no articles exist")
    void getAllArticles_ReturnsEmptyList() {
        when(articleRepository.findAll()).thenReturn(new ArrayList<>());

        List<Article> articles = articleService.getAllArticles();

        assertNotNull(articles);
        assertTrue(articles.isEmpty());
        verify(articleRepository).findAll();
    }

    @Test
    @DisplayName("Should throw ServiceException when repository throws exception")
    void getAllArticles_Exception() {
        when(articleRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        ServiceException exception =
                assertThrows(ServiceException.class, () -> articleService.getAllArticles());

        assertEquals("Failed to fetch Articles", exception.getMessage());
        verify(articleRepository).findAll();
    }

    @Test
    @DisplayName("Should return article when found by slug")
    void getArticleContentBySlug_Found() {
        String slug = "test-article";
        ArticleContentEntity contentEntity = ArticleContentEntity.builder().slug(slug).build();
        ArticleContent expectedContent = ArticleContent.builder().slug(slug).build();

        when(articleContentRepository.findById(slug)).thenReturn(Optional.of(contentEntity));

        ArticleContent articleContent = articleService.getArticleContentBySlug(slug);

        assertNotNull(articleContent);
        assertEquals(expectedContent.getSlug(), articleContent.getSlug());
        verify(articleContentRepository).findById(slug);
    }

    @Test
    @DisplayName("Should return null when article not found by slug")
    void getArticleContentBySlug_NotFound() {
        when(articleContentRepository.findById("nonexistent")).thenReturn(Optional.empty());

        ArticleContent articleContent = articleService.getArticleContentBySlug("nonexistent");

        assertNull(articleContent);
        verify(articleContentRepository).findById("nonexistent");
    }
}
