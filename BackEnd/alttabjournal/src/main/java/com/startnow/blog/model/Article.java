package com.startnow.blog.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Article {

    @NonNull
    public String slug;
    public String fullContent;
}
