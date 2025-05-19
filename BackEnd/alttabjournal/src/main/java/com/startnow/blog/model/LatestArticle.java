package com.startnow.blog.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LatestArticle {
    @NonNull
    public String title;
    @NonNull
    public String description;
    @NonNull
    public String slug;
}
