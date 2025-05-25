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
    private String agentName;
    private String description;
    private String authorName;
    private String createdAt;
    private String updatedAt;
    private String status;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("agentId")
    public Integer getAgentId() {
        return agentId;
    }

    @DynamoDbAttribute("agentName")
    public String getAgentName() {
        return agentName;
    }

    @DynamoDbAttribute("fullContent")
    public String getDescription() {
        return description;
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

