package com.startnow.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleContent {
    public String slug;
    public String fullContent;
    public String authorName;
    public String createdAt;
    public String updatedAt;
    private String status;
}
