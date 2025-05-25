package com.startnow.blog.controller_tests;

import com.startnow.blog.controller.ArticleController;
import com.startnow.blog.exception_handler.GlobalExceptionHandler;
import com.startnow.blog.model.Article;
import com.startnow.blog.model.ArticleContent;
import com.startnow.blog.service.ArticleService;
import com.startnow.blog.service.ArticleServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ArticleControllerTests {

    private MockMvc mockMvc;

    @Mock
    private ArticleServiceInterface articleService;

    @InjectMocks
    private ArticleController articleController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(articleController)
                .setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    @DisplayName("Should return list of articles when articles exist")
    void getArticles_ReturnsList() throws Exception {
        List<Article> mockArticles = List.of(
                Article.builder().title("Title 1").description("Description 1").slug("slug-1")
                        .build(),
                Article.builder().title("Title 2").description("Description 2").slug("slug-2")
                        .build());

        when(articleService.getAllArticles()).thenReturn(mockArticles);

        mockMvc.perform(get("/api/articles").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Title 1")))
                .andExpect(jsonPath("$[0].description", is("Description 1")))
                .andExpect(jsonPath("$[0].slug", is("slug-1")))
                .andExpect(jsonPath("$[1].title", is("Title 2")))
                .andExpect(jsonPath("$[1].description", is("Description 2")))
                .andExpect(jsonPath("$[1].slug", is("slug-2")));
    }

    @Test
    @DisplayName("Should return empty list when no articles exist")
    void getArticles_ReturnsEmptyList() throws Exception {
        when(articleService.getAllArticles()).thenReturn(List.of());

        mockMvc.perform(get("/api/articles").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("Should return article when found by slug")
    void getArticleBySlug_Found() throws Exception {
        ArticleContent mockArticleContent =
                ArticleContent.builder().slug("slug-1").fullContent("Content of Article 1").build();

        when(articleService.getArticleContentBySlug("slug-1")).thenReturn(mockArticleContent);

        mockMvc.perform(
                get("/api/articles/{slug}", "slug-1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.slug", is("slug-1")))
                .andExpect(jsonPath("$.fullContent", is("Content of Article 1")));
    }

    @Test
    @DisplayName("Should return 404 when article not found by slug")
    void getArticleBySlug_NotFound() throws Exception {
        when(articleService.getArticleContentBySlug("nonexistent")).thenReturn(null);

        mockMvc.perform(
                get("/api/articles/{slug}", "nonexistent").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should handle null slug gracefully")
    void getArticleBySlug_NullSlug() throws Exception {
        // When & Then
        mockMvc.perform(
                get("/articles/{slug}", (Object) null).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should handle empty slug gracefully")
    void getArticleBySlug_EmptySlug() throws Exception {
        // When & Then
        mockMvc.perform(get("/articles/{slug}", "").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
