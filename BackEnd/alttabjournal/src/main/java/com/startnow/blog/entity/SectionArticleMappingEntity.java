package com.startnow.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SectionArticleMappingEntity {
    private String sectionId;
    private String articleId;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("sectionId")
    public String getSectionId() {
        return sectionId;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute("articleId")
    public String getArticleId() {
        return articleId;
    }

}

