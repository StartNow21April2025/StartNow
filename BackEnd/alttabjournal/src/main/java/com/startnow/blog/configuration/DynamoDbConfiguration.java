package com.startnow.blog.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
@Slf4j
public class DynamoDbConfiguration {

    @Value("${aws.region:ap-south-1}") // provide a default value
    private String awsRegion;

    @Bean
    public DynamoDbClient dynamoDbClient() {
        log.info("Configuring DynamoDB client for region: {}", awsRegion);
        return DynamoDbClient.builder().region(Region.of(awsRegion)).build();
    }

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient() {
        return DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoDbClient()).build();
    }
}

