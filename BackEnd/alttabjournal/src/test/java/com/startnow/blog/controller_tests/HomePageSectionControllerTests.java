package com.startnow.blog.controller_tests;

import com.startnow.blog.controller.HomePageSectionController;
import com.startnow.blog.exception_handler.GlobalExceptionHandler;
import com.startnow.blog.model.HomePageSectionContent;
import com.startnow.blog.service.SectionArticleMappingServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class HomePageSectionControllerTests {

    private MockMvc mockMvc;

    @InjectMocks
    private HomePageSectionController homePageSectionController;

    @Mock
    private SectionArticleMappingServiceInterface sectionArticleMappingService;

    private HomePageSectionContent testContent;

    @BeforeEach
    void setUp() {
        testContent = HomePageSectionContent.builder().slug("test-article").title("Test Article")
                .tag("Technology").imageUrl("http://example.com/image.jpg").author("Test Author")
                .date("2024-01-01").build();

        mockMvc = MockMvcBuilders.standaloneSetup(homePageSectionController)
                .setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    void getHomePageSections_Success() throws Exception {
        List<HomePageSectionContent> contentList = Arrays.asList(testContent);
        when(sectionArticleMappingService.getArticlesBySection("hero")).thenReturn(contentList);

        mockMvc.perform(get("/api/homePageSections").param("section", "hero"))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].slug").value("test-article"))
                .andExpect(jsonPath("$[0].title").value("Test Article"))
                .andExpect(jsonPath("$[0].tag").value("Technology"))
                .andExpect(jsonPath("$[0].author").value("Test Author"));

        verify(sectionArticleMappingService).getArticlesBySection("hero");
    }

    @Test
    void getHomePageSections_EmptySection() throws Exception {
        mockMvc.perform(get("/api/homePageSections").param("section", ""))
                .andExpect(status().isNotFound());

        verify(sectionArticleMappingService, never()).getArticlesBySection(anyString());
    }

    @Test
    void getHomePageSections_NullSection() throws Exception {
        mockMvc.perform(get("/api/homePageSections")).andExpect(status().isBadRequest());

        verify(sectionArticleMappingService, never()).getArticlesBySection(anyString());
    }

    @Test
    void getHomePageSections_NoContentFound() throws Exception {
        when(sectionArticleMappingService.getArticlesBySection("nonexistent")).thenReturn(null);

        mockMvc.perform(get("/api/homePageSections").param("section", "nonexistent"))
                .andExpect(status().isNotFound());

        verify(sectionArticleMappingService).getArticlesBySection("nonexistent");
    }

    @Test
    void getHomePageSections_EmptyList() throws Exception {
        when(sectionArticleMappingService.getArticlesBySection("empty"))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/homePageSections").param("section", "empty"))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(0)));

        verify(sectionArticleMappingService).getArticlesBySection("empty");
    }
}
