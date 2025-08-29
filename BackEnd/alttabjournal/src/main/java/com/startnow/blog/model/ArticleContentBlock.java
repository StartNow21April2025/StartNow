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
public class ArticleContentBlock {
    public String type; // e.g. "paragraph", "list", "quote", "code", "image", "divider"
    public String text; // for paragraph, quote, code
    public List<String> items; // for list type
    public String src; // optional, for image
    public String alt; // optional, for image
}
