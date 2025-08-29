package com.startnow.blog.repository;

import com.startnow.blog.entity.SectionArticleMappingEntity;

import java.util.List;

// Section Article Mapping Repository Interface
public interface ISectionArticleMappingRepository {

    public SectionArticleMappingEntity save(SectionArticleMappingEntity article);

    public void delete(String id);

    public List<SectionArticleMappingEntity> findBySectionId(String id);

    public void deleteById(String sectionId, String articleId);
}
