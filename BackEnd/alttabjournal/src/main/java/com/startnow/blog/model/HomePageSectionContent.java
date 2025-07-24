package com.startnow.blog.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HomePageSectionContent {
    private String slug;
    public String title;
    public String tag;
    public String description;
    public String imageUrl;
    public String author;
    public String date;
}


