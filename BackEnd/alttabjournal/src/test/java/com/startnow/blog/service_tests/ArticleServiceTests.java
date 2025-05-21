package com.startnow.blog.service_tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.startnow.blog.model.Article;
import com.startnow.blog.model.LatestArticle;
import com.startnow.blog.service.ArticleService;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;

class ArticleServiceTests {

    @Mock
    private DynamoDbClient dynamoDbClient;

    @InjectMocks
    private ArticleService articleService;

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
    @DisplayName("Should return all articles when articles exist")
    void getAllArticles_ReturnsList() {
        Map<String, AttributeValue> item1 =
                Map.of("title", AttributeValue.fromS("Article 1"), "description",
                        AttributeValue.fromS("Desc 1"), "slug", AttributeValue.fromS("article-1"));
        Map<String, AttributeValue> item2 =
                Map.of("title", AttributeValue.fromS("Article 2"), "description",
                        AttributeValue.fromS("Desc 2"), "slug", AttributeValue.fromS("article-2"));
        ScanResponse mockResponse = ScanResponse.builder().items(List.of(item1, item2)).build();
        when(dynamoDbClient.scan(any(ScanRequest.class))).thenReturn(mockResponse);

        List<LatestArticle> articles = articleService.getAllArticles();

        assertAll(() -> assertEquals(2, articles.size()),
                () -> assertEquals("Article 1", articles.get(0).getTitle()),
                () -> assertEquals("Desc 1", articles.get(0).getDescription()),
                () -> assertEquals("article-1", articles.get(0).getSlug()),
                () -> assertEquals("Article 2", articles.get(1).getTitle()),
                () -> assertEquals("Desc 2", articles.get(1).getDescription()),
                () -> assertEquals("article-2", articles.get(1).getSlug()));
    }

    @Test
    @DisplayName("Should return empty list when no articles exist")
    void getAllArticles_ReturnsEmptyList() {
        when(dynamoDbClient.scan(any(ScanRequest.class)))
                .thenReturn(ScanResponse.builder().build());

        List<LatestArticle> articles = articleService.getAllArticles();

        assertNotNull(articles);
        assertTrue(articles.isEmpty());
    }

    @Test
    @DisplayName("Should return article when found by slug")
    void getArticleBySlug_Found() {
        Map<String, AttributeValue> item = Map.of("slug", AttributeValue.fromS("article-1"),
                "fullContent", AttributeValue.fromS("Detailed content of article 1"));
        ScanResponse mockResponse = ScanResponse.builder().items(List.of(item)).build();
        when(dynamoDbClient.scan(any(ScanRequest.class))).thenReturn(mockResponse);

        Article article = articleService.getArticleBySlug("article-1");

        assertAll(() -> assertNotNull(article), () -> assertEquals("article-1", article.getSlug()),
                () -> assertEquals("Detailed content of article 1", article.getFullContent()));
    }

    @Test
    @DisplayName("Should return null when article not found by slug")
    void getArticleBySlug_NotFound() {
        when(dynamoDbClient.scan(any(ScanRequest.class)))
                .thenReturn(ScanResponse.builder().build());

        Article article = articleService.getArticleBySlug("nonexistent");

        assertNull(article);
    }

    @Test
    @DisplayName("Should return null when slug is null")
    void getArticleBySlug_NullSlug() {
        when(dynamoDbClient.scan(any(ScanRequest.class)))
                .thenReturn(ScanResponse.builder().build());

        Article article = articleService.getArticleBySlug(null);

        assertNull(article);
    }
}
