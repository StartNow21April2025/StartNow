package com.startnow.blog.service;

import com.startnow.blog.entity.ArticleEntity;
import com.startnow.blog.entity.SectionArticleMappingEntity;
import com.startnow.blog.exception.ServiceException;
import com.startnow.blog.model.HomePageSectionContent;
import com.startnow.blog.repository.IArticleRepository;
import com.startnow.blog.repository.ISectionArticleMappingRepository;
import com.startnow.blog.util.ArticleUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SectionArticleMappingService implements SectionArticleMappingServiceInterface {

    private static final Logger logger =
            LoggerFactory.getLogger(SectionArticleMappingService.class);
    private final IArticleRepository articleRepository;
    private final ISectionArticleMappingRepository sectionArticleMappingRepository;
    private final ArticleServiceInterface articleService;

    /**
     * Retrieves all articles of a section with summary information.
     */
    @Override
    public List<HomePageSectionContent> getArticlesBySection(String section) {
        try {
            List<SectionArticleMappingEntity> sectionArticleMappingEntityList =
                    sectionArticleMappingRepository.findBySectionId(section);
            List<Integer> idList = sectionArticleMappingEntityList.stream()
                    .map(s -> Integer.valueOf(s.getArticleId())).collect(Collectors.toList());
            List<ArticleEntity> articleEntityList = articleRepository.findByIds(idList);
            if (articleEntityList.isEmpty()) {
                logger.warn("Articles for section {} not found in the database.", section);
                return new ArrayList<>();
            }
            logger.info("Fetched {} articles for section {}", articleEntityList.size(), section);
            return articleEntityList.stream().filter(Objects::nonNull)
                    .map(ArticleUtil::convertToHomePageSectionContent).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching Articles: {}", e.getMessage(), e);
            throw new ServiceException("Failed to fetch Articles", e);
        }
    }
}
