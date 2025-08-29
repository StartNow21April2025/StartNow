package com.startnow.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.LocalDateTime;

@DynamoDbBean
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AgentEntity {
    private Integer agentId;
    private String name;
    private String title;
    private String quote;
    private String description;
    private String imageUrl;
    private String strengths;
    private String weaknesses;
    private String authorName;
    private String createdAt;
    private String updatedAt;
    private String status;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("agentId")
    public Integer getAgentId() {
        return agentId;
    }

    @DynamoDbAttribute("name")
    public String getName() {
        return name;
    }

    @DynamoDbAttribute("title")
    public String getTitle() {
        return title;
    }

    @DynamoDbAttribute("quote")
    public String getQuote() {
        return quote;
    }

    @DynamoDbAttribute("description")
    public String getDescription() {
        return description;
    }

    @DynamoDbAttribute("imageUrl")
    public String getImageUrl() {
        return imageUrl;
    }

    @DynamoDbAttribute("strengths")
    public String getStrengths() {
        return strengths;
    }

    @DynamoDbAttribute("weaknesses")
    public String getWeaknesses() {
        return weaknesses;
    }

    @DynamoDbAttribute("authorName")
    public String getAuthorName() {
        return authorName;
    }

    @DynamoDbAttribute("createdAt")
    public String getCreatedAt() {
        return createdAt;
    }

    @DynamoDbAttribute("updatedAt")
    public String getUpdatedAt() {
        return updatedAt;
    }

    @DynamoDbAttribute("status")
    public String getStatus() {
        return status;
    }
}

