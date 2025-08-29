package com.startnow.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleContent {
    public String slug;
    public String author;
    public String date;
    public String title;
    public ArrayList<ArticleSection> sections;
    private String status;
}
