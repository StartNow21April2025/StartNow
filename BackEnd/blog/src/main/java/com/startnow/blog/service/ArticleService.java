// src/main/java/com/startnow/blog/service/ArticleService.java
package com.startnow.blog.service;

import com.startnow.blog.model.Article;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.*;

@Service
public class ArticleService {

    private final DynamoDbClient dynamoDbClient;
    private final String tableName = "Articles";

    public ArticleService(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    public List<Article> getAllArticles() {
        ScanRequest scanRequest = ScanRequest.builder()
                .tableName(tableName)
                .build();

        ScanResponse response = dynamoDbClient.scan(scanRequest);

        List<Article> articles = new ArrayList<>();
        for (Map<String, AttributeValue> item : response.items()) {
            String title = item.getOrDefault("title", AttributeValue.fromS("")).s();
            String description = item.getOrDefault("description", AttributeValue.fromS("")).s();
            articles.add(new Article(title, description));
        }
        return articles;
    }
}
