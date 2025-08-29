package com.startnow.blog.configuration;

import com.startnow.blog.entity.*;
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

    @Value("${dynamodb.tables.section-article-mapping}")
    private String sectionArticleMappingTableName;

    @Value("${dynamodb.tables.user}")
    private String userTableName;

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

    @Bean
    public DynamoDbTable<SectionArticleMappingEntity> sectionArticleMappingTable(
            DynamoDbEnhancedClient enhancedClient) {
        log.info("Configuring DynamoDB sectionArticleMapping table: {}",
                sectionArticleMappingTableName);
        return enhancedClient.table(sectionArticleMappingTableName,
                TableSchema.fromBean(SectionArticleMappingEntity.class));
    }

    @Bean
    public DynamoDbTable<UserEntity> userTable(DynamoDbEnhancedClient enhancedClient) {
        log.info("Configuring DynamoDB User table: {}", userTableName);
        return enhancedClient.table(userTableName, TableSchema.fromBean(UserEntity.class));
    }
}


