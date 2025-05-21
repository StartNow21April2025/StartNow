package com.startnow.blog.service;

import com.startnow.blog.model.Article;
import com.startnow.blog.model.LatestArticle;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;

@Service
public class ArticleService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleService.class);

    private final DynamoDbClient dynamoDbClient;
    private final String articlesTableName;
    private final String articlesContentTableName;

    public ArticleService(DynamoDbClient dynamoDbClient,
            @Value("${dynamodb.articlesTableName:Articles}") String articlesTableName,
            @Value("${dynamodb.articlesContentTableName:ArticleContent}") String articlesContentTableName) {
        this.dynamoDbClient = dynamoDbClient;
        this.articlesTableName = articlesTableName;
        this.articlesContentTableName = articlesContentTableName;
    }

    /** Retrieves all articles with summary information. */
    public List<LatestArticle> getAllArticles() {
        ScanRequest scanRequest = ScanRequest.builder().tableName(articlesTableName)
                .projectionExpression("title, description, slug").build();

        ScanResponse response = dynamoDbClient.scan(scanRequest);
        logger.info("Fetched {} articles from table {}", response.count(), articlesTableName);

        List<LatestArticle> articles = new ArrayList<>();
        for (Map<String, AttributeValue> item : response.items()) {
            String title = item.getOrDefault("title", AttributeValue.fromS("")).s();
            String description = item.getOrDefault("description", AttributeValue.fromS("")).s();
            String slug = item.getOrDefault("slug", AttributeValue.fromS("")).s();
            articles.add(new LatestArticle(title, description, slug));
        }
        return articles;
    }

    /** Retrieves a full article by its slug. */
    public Article getArticleBySlug(String slug) {
        ScanRequest scanRequest = ScanRequest.builder().tableName(articlesContentTableName)
                .filterExpression("#slug = :slug").expressionAttributeNames(Map.of("#slug", "slug"))
                .expressionAttributeValues(Map.of(":slug", AttributeValue.fromS(slug)))
                .projectionExpression("slug, fullContent").build();

        ScanResponse response = dynamoDbClient.scan(scanRequest);
        logger.info("Fetched {} articles with slug '{}' from table {}", response.count(), slug,
                articlesContentTableName);

        if (response.items().isEmpty()) {
            logger.warn("Article not found for slug: {}", slug);
            return null;
        }

        Map<String, AttributeValue> item = response.items().get(0);
        return new Article(item.getOrDefault("slug", AttributeValue.fromS("")).s(),
                item.getOrDefault("fullContent", AttributeValue.fromS("")).s());
    }
}
