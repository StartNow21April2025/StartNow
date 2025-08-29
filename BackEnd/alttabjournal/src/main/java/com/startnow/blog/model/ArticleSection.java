package com.startnow.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleSection {
    public String id;
    public String title;
    private List<ArticleContentBlock> content;
}
