package com.startnow.blog.controller_tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.startnow.blog.controller.ArticleController;
import com.startnow.blog.model.Article;
import com.startnow.blog.model.LatestArticle;
import com.startnow.blog.service.ArticleService;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

class ArticleControllerTests {

    @Mock
    private ArticleService articleService;

    @InjectMocks
    private ArticleController articleController;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (mocks != null) {
            mocks.close();
        }
    }

    @Test
    @DisplayName("Should return list of articles when articles exist")
    void getArticles_ReturnsList() {
        List<LatestArticle> mockArticles =
                List.of(new LatestArticle("Title 1", "Description 1", "slug-1"),
                        new LatestArticle("Title 2", "Description 2", "slug-2"));
        when(articleService.getAllArticles()).thenReturn(mockArticles);

        List<LatestArticle> articles = articleController.getArticles();

        assertAll(() -> assertEquals(2, articles.size()),
                () -> assertEquals("Title 1", articles.get(0).getTitle()),
                () -> assertEquals("Description 1", articles.get(0).getDescription()),
                () -> assertEquals("slug-1", articles.get(0).getSlug()),
                () -> assertEquals("Title 2", articles.get(1).getTitle()),
                () -> assertEquals("Description 2", articles.get(1).getDescription()),
                () -> assertEquals("slug-2", articles.get(1).getSlug()));
    }

    @Test
    @DisplayName("Should return empty list when no articles exist")
    void getArticles_ReturnsEmptyList() {
        when(articleService.getAllArticles()).thenReturn(List.of());

        List<LatestArticle> articles = articleController.getArticles();

        assertNotNull(articles);
        assertTrue(articles.isEmpty());
    }

    @Test
    @DisplayName("Should return article when found by slug")
    void getArticleBySlug_Found() {
        Article mockArticle = new Article("slug-1", "Content of Article 1");
        when(articleService.getArticleBySlug("slug-1")).thenReturn(mockArticle);

        ResponseEntity<Article> response = articleController.getArticle("slug-1");

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("slug-1", response.getBody().getSlug());
        assertEquals("Content of Article 1", response.getBody().getFullContent());
    }

    @Test
    @DisplayName("Should return 404 when article not found by slug")
    void getArticleBySlug_NotFound() {
        when(articleService.getArticleBySlug("nonexistent")).thenReturn(null);

        ResponseEntity<Article> response = articleController.getArticle("nonexistent");

        assertEquals(404, response.getStatusCode().value());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Should handle null slug gracefully")
    void getArticleBySlug_NullSlug() {
        when(articleService.getArticleBySlug(null)).thenReturn(null);

        ResponseEntity<Article> response = articleController.getArticle(null);

        assertEquals(404, response.getStatusCode().value());
        assertNull(response.getBody());
    }
}
