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
public class ArticleEntity {
    private Integer titleId;
    private String slug;
    private String title;
    private String tag;
    private String description;
    private String imageUrl;
    private String authorName;
    private String createdAt;
    private String updatedAt;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("titleId")
    public Integer getTitleId() {
        return titleId;
    }

    @DynamoDbAttribute("slug")
    public String getSlug() {
        return slug;
    }

    @DynamoDbAttribute("tag")
    public String getTag() {
        return tag;
    }

    @DynamoDbAttribute("title")
    public String getTitle() {
        return title;
    }

    @DynamoDbAttribute("description")
    public String getDescription() {
        return description;
    }

    @DynamoDbAttribute("imageUrl")
    public String getImageUrl() {
        return imageUrl;
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

}

