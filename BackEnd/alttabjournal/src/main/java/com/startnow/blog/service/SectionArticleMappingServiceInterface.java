package com.startnow.blog.service;

import com.startnow.blog.model.HomePageSectionContent;

import java.util.List;

public interface SectionArticleMappingServiceInterface {

    public List<HomePageSectionContent> getArticlesBySection(String section);

}
