package com.startnow.blog;

import com.startnow.blog.entity.AgentEntity;
import com.startnow.blog.entity.ArticleContentEntity;
import com.startnow.blog.entity.ArticleEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class StartNowApplicationTests {

    @Mock
    private DynamoDbClient dynamoDbClient;

    @Mock
    private DynamoDbEnhancedClient dynamoDbEnhancedClient;

    @Mock
    private DynamoDbTable<ArticleEntity> articleTable;

    @Mock
    private DynamoDbTable<ArticleContentEntity> articleContentTable;

    @Mock
    private DynamoDbTable<AgentEntity> agentTable;

    @Test
    void contextLoads() {}
}

