package com.startnow.blog.controller;

import com.startnow.blog.model.HomePageSectionContent;
import com.startnow.blog.service.SectionArticleMappingServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** REST controller for managing Blog home page. */
@RestController
@RequestMapping("/api")
@Tag(name = "Home Page Section Controller",
        description = "APIs for managing Home Page Section of Blog")
public class HomePageSectionController {
    private final SectionArticleMappingServiceInterface sectionArticleMappingService;

    public HomePageSectionController(
            SectionArticleMappingServiceInterface sectionArticleMappingService) {
        this.sectionArticleMappingService = sectionArticleMappingService;
    }

    /**
     * Retrieves relevant Section with Articles.
     *
     * @return a list of articles in HomePageSectionContent format
     */
    @GetMapping("/homePageSections")
    @Operation(summary = "Get all sections")
    public ResponseEntity<List<HomePageSectionContent>> getHomePageSections(
            @RequestParam String section) {
        if (section == null || section.trim().isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<HomePageSectionContent> homePageSectionContent =
                sectionArticleMappingService.getArticlesBySection(section);
        return homePageSectionContent != null ? ResponseEntity.ok(homePageSectionContent)
                : ResponseEntity.notFound().build();
    }
}
