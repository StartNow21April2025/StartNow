package com.startnow.blog.servicetests;

import com.startnow.blog.model.Article;
import com.startnow.blog.model.LatestArticle;
import com.startnow.blog.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArticleServiceTests {

    @Mock
    private DynamoDbClient dynamoDbClient;

    @InjectMocks
    private ArticleService articleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllArticles() {
        Map<String, AttributeValue> item1 = Map.of(
                "title", AttributeValue.fromS("Article 1"),
                "description", AttributeValue.fromS("Desc 1"),
                "slug", AttributeValue.fromS("article-1")
        );

        Map<String, AttributeValue> item2 = Map.of(
                "title", AttributeValue.fromS("Article 2"),
                "description", AttributeValue.fromS("Desc 2"),
                "slug", AttributeValue.fromS("article-2")
        );

        ScanResponse mockResponse = ScanResponse.builder().items(List.of(item1, item2)).build();
        when(dynamoDbClient.scan(any(ScanRequest.class))).thenReturn(mockResponse);

        List<LatestArticle> articles = articleService.getAllArticles();

        assertEquals(2, articles.size());
        assertEquals("Article 1", articles.get(0).getTitle());
        assertEquals("Article 2", articles.get(1).getTitle());
    }

    @Test
    void testGetAllArticlesEmpty() {
        when(dynamoDbClient.scan(any(ScanRequest.class))).thenReturn(ScanResponse.builder().build());

        List<LatestArticle> articles = articleService.getAllArticles();

        assertTrue(articles.isEmpty());
    }

    @Test
    void testGetArticleBySlug() {
        Map<String, AttributeValue> item = Map.of(
                "slug", AttributeValue.fromS("article-1"),
                "fullContent", AttributeValue.fromS("Detailed content of article 1")
        );

        ScanResponse mockResponse = ScanResponse.builder().items(List.of(item)).build();
        when(dynamoDbClient.scan(any(ScanRequest.class))).thenReturn(mockResponse);

        Article article = articleService.getArticleBySlug("article-1");

        assertNotNull(article);
        assertEquals("article-1", article.getSlug());
        assertEquals("Detailed content of article 1", article.getFullContent());
    }

    @Test
    void testGetArticleBySlugNotFound() {
        when(dynamoDbClient.scan(any(ScanRequest.class))).thenReturn(ScanResponse.builder().build());

        Article article = articleService.getArticleBySlug("nonexistent");

        assertNull(article);
    }
}
