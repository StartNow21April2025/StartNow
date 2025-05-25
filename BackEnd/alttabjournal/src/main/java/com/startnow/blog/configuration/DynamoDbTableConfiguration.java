package com.startnow.blog.configuration;

import com.startnow.blog.entity.AgentEntity;
import com.startnow.blog.entity.ArticleContentEntity;
import com.startnow.blog.entity.ArticleEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;


@Configuration
@Slf4j
public class DynamoDbTableConfiguration {

    @Value("${dynamodb.tables.articles}")
    private String articlesTableName;

    @Value("${dynamodb.tables.articles-content}")
    private String articlesContentTableName;

    @Value("${dynamodb.tables.agent}")
    private String agentTableName;

    @Bean
    public DynamoDbTable<ArticleEntity> articleTable(DynamoDbEnhancedClient enhancedClient) {
        log.info("Configuring DynamoDB article table: {}", articlesTableName);
        return enhancedClient.table(articlesTableName, TableSchema.fromBean(ArticleEntity.class));
    }

    @Bean
    public DynamoDbTable<ArticleContentEntity> articleContentTable(
            DynamoDbEnhancedClient enhancedClient) {
        log.info("Configuring DynamoDB article content table: {}", articlesContentTableName);
        return enhancedClient.table(articlesContentTableName,
                TableSchema.fromBean(ArticleContentEntity.class));
    }

    @Bean
    public DynamoDbTable<AgentEntity> agentTable(DynamoDbEnhancedClient enhancedClient) {
        log.info("Configuring DynamoDB agent table: {}", agentTableName);
        return enhancedClient.table(agentTableName, TableSchema.fromBean(AgentEntity.class));
    }
}


