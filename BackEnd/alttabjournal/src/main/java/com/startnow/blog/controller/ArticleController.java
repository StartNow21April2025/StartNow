package com.startnow.blog.controller;

import com.startnow.blog.model.Article;
import com.startnow.blog.model.ArticleContent;
import com.startnow.blog.service.ArticleServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** REST controller for managing Article entities. */
@RestController
@RequestMapping("/api")
@Tag(name = "Article Controller", description = "APIs for managing Article")
public class ArticleController {

    private final ArticleServiceInterface articleService;

    /**
     * Constructs an ArticleController with the given ArticleService.
     *
     * @param articleService the service to handle article operations
     */
    public ArticleController(ArticleServiceInterface articleService) {
        this.articleService = articleService;
    }

    /**
     * Retrieves all articles.
     *
     * @return a list of the latest articles
     */
    @GetMapping("/articles")
    @Operation(summary = "Get all Articles")
    public List<Article> getArticles() {
        return articleService.getAllArticles();
    }

    /**
     * Retrieves a specific article by its slug.
     *
     * @param slug the slug of the article to retrieve
     * @return the Article if found, or HTTP 404 if not found
     */
    @GetMapping("/articles/{slug}")
    @Operation(summary = "Get a specific Article")
    public ResponseEntity<ArticleContent> getArticle(@PathVariable(required = false) String slug) {
        if (slug == null || slug.trim().isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ArticleContent articleContent = articleService.getArticleContentBySlug(slug);
        return articleContent != null ? ResponseEntity.ok(articleContent)
                : ResponseEntity.notFound().build();
    }

}
