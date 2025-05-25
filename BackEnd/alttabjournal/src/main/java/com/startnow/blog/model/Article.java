package com.startnow.blog.model;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    public Integer articleId;
    public String title;
    public String description;
    @NonNull
    public String slug;
}
