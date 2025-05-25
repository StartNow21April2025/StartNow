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
public class ArticleContentEntity {
    private String slug;
    private String fullContent;
    private String authorName;
    private String createdAt;
    public String updatedAt;
    private String status;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("slug")
    public String getSlug() {
        return slug;
    }

    @DynamoDbAttribute("fullContent")
    public String getFullContent() {
        return fullContent;
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

