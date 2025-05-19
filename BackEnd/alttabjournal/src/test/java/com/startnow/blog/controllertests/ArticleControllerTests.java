package com.startnow.blog.controllertests;

import com.startnow.blog.controller.ArticleController;
import com.startnow.blog.model.Article;
import com.startnow.blog.model.LatestArticle;
import com.startnow.blog.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArticleControllerTests {

    @Mock
    private ArticleService articleService;

    @InjectMocks
    private ArticleController articleController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetArticles() {
        List<LatestArticle> mockArticles = List.of(
                new LatestArticle("Title 1", "Description 1", "slug-1"),
                new LatestArticle("Title 2", "Description 2", "slug-2")
        );

        when(articleService.getAllArticles()).thenReturn(mockArticles);

        List<LatestArticle> articles = articleController.getArticles();

        assertEquals(2, articles.size());
        assertEquals("Title 1", articles.get(0).getTitle());
        assertEquals("Title 2", articles.get(1).getTitle());
    }

    @Test
    void testGetArticlesEmpty() {
        when(articleService.getAllArticles()).thenReturn(List.of());

        List<LatestArticle> articles = articleController.getArticles();

        assertTrue(articles.isEmpty());
    }

    @Test
    void testGetArticleBySlug() {
        Article mockArticle = new Article("slug-1", "Content of Article 1");
        when(articleService.getArticleBySlug("slug-1")).thenReturn(mockArticle);

        ResponseEntity<Article> response = articleController.getArticle("slug-1");

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("slug-1", response.getBody().getSlug());
    }

    @Test
    void testGetArticleBySlugNotFound() {
        when(articleService.getArticleBySlug("nonexistent")).thenReturn(null);

        ResponseEntity<Article> response = articleController.getArticle("nonexistent");

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }
}
