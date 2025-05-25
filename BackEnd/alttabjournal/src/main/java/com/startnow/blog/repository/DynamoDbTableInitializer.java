package com.startnow.blog.repository;

import com.startnow.blog.entity.ArticleContentEntity;
import com.startnow.blog.entity.ArticleEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.model.CreateTableEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput;
import software.amazon.awssdk.services.dynamodb.model.ResourceInUseException;

import java.util.HashMap;
import java.util.Map;

@Repository
@Slf4j
public class DynamoDbTableInitializer {
    private final DynamoDbEnhancedClient enhancedClient;
    private final Map<String, DynamoDbTable<?>> tables;

    @Value("${spring.profiles.active:}")
    private String activeProfile;

    public DynamoDbTableInitializer(DynamoDbEnhancedClient enhancedClient,
            DynamoDbTable<ArticleEntity> articleTable,
            DynamoDbTable<ArticleContentEntity> contentTable) {
        this.enhancedClient = enhancedClient;
        this.tables = new HashMap<>();
        tables.put("articles", articleTable);
        tables.put("contents", contentTable);

        if (isLocalProfile()) {
            createTablesIfNotExist();
        }
    }

    private boolean isLocalProfile() {
        return "local".equals(activeProfile);
    }

    private void createTablesIfNotExist() {
        // Create Content table (no GSI needed)
        createBasicTable(tables.get("contents"));
    }

    private void createBasicTable(DynamoDbTable<?> table) {
        try {
            CreateTableEnhancedRequest createTableRequest = CreateTableEnhancedRequest.builder()
                    .provisionedThroughput(ProvisionedThroughput.builder().readCapacityUnits(5L)
                            .writeCapacityUnits(5L).build())
                    .build();

            table.createTable(createTableRequest);
            log.info("Table {} created successfully", table.tableName());
        } catch (ResourceInUseException e) {
            log.info("Table {} already exists", table.tableName());
        } catch (Exception e) {
            log.error("Error creating table {}: {}", table.tableName(), e.getMessage());
        }
    }
}

