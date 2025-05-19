package com.startnow.blog.service;

import com.startnow.blog.model.Article;
import com.startnow.blog.model.LatestArticle;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.*;

@Service
public class ArticleService {

    private final DynamoDbClient dynamoDbClient;
    private final String articlesTableName = "Articles";
    private final String articlesContentTableName = "ArticleContent";

    public ArticleService(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    // ✅ Get all articles
    public List<LatestArticle> getAllArticles() {
        ScanRequest scanRequest = ScanRequest.builder()
                .tableName(articlesTableName)
                .projectionExpression("title, description, slug, fullContent") // Retrieve only required attributes
                .build();

        ScanResponse response = dynamoDbClient.scan(scanRequest);
        System.out.println("DynamoDB Response: " + response); // Debugging

        List<LatestArticle> articles = new ArrayList<>();
        for (Map<String, AttributeValue> item : response.items()) {
            String title = item.getOrDefault("title", AttributeValue.fromS("")).s();
            String description = item.getOrDefault("description", AttributeValue.fromS("")).s();
            String slug = item.getOrDefault("slug", AttributeValue.fromS("")).s();
            articles.add(new LatestArticle(title, description, slug));
        }
        return articles;
    }

    // ✅ Fetch an article by slug
    public Article getArticleBySlug(String slug) {
        ScanRequest scanRequest = ScanRequest.builder()
                .tableName(articlesContentTableName)
                .filterExpression("#slug = :slug") // Corrected filter expression format
                .expressionAttributeNames(Map.of("#slug", "slug")) // Define attribute alias
                .expressionAttributeValues(Map.of(":slug", AttributeValue.fromS(slug)))
                .projectionExpression("slug, fullContent") // Retrieve only necessary fields
                .build();

        ScanResponse response = dynamoDbClient.scan(scanRequest);
        System.out.println("Fetching article by slug: " + slug + "\nResponse: " + response); // Debugging

        if (response.items().isEmpty()) {
            System.out.println("Article not found for slug: " + slug);
            return null;
        }

        Map<String, AttributeValue> item = response.items().get(0);
        return new Article(
                item.getOrDefault("slug", AttributeValue.fromS("")).s(),
                item.getOrDefault("fullContent", AttributeValue.fromS("")).s() // Fetch full blog content
        );
    }
}