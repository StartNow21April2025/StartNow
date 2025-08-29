package com.startnow.blog.entity;

import com.startnow.blog.model.ArticleSection;
import com.startnow.blog.util.ArticleSectionListConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbConvertedBy;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.ArrayList;

@DynamoDbBean
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ArticleContentEntity {
    private String slug;
    private String author;
    private String date;
    private String title;
    private ArrayList<ArticleSection> sections;
    private String status;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("slug")
    public String getSlug() {
        return slug;
    }

    @DynamoDbAttribute("author")
    public String getAuthor() {
        return author;
    }

    @DynamoDbAttribute("date")
    public String getDate() {
        return date;
    }

    @DynamoDbAttribute("title")
    public String getTitle() {
        return title;
    }

    @DynamoDbAttribute("sections")
    @DynamoDbConvertedBy(ArticleSectionListConverter.class)
    public ArrayList<ArticleSection> getSections() {
        return sections != null ? sections : new ArrayList<>();
    }

    @DynamoDbAttribute("status")
    public String getStatus() {
        return status;
    }
}

