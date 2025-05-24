package com.startnow.blog.controller;

import com.startnow.blog.model.Article;
import com.startnow.blog.model.LatestArticle;
import com.startnow.blog.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** REST controller for managing Article entities. */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Allow frontend to call this API
public class ArticleController {

    private final ArticleService articleService;

    /**
     * Constructs an ArticleController with the given ArticleService.
     *
     * @param articleService the service to handle article operations
     */
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * Retrieves all articles.
     *
     * @return a list of the latest articles
     */
    @GetMapping("/articles")
    @Operation(summary = "Get all Articles")
    public List<LatestArticle> getArticles() {
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
    public ResponseEntity<Article> getArticle(@PathVariable String slug) {
        Article article = articleService.getArticleBySlug(slug);
        return article != null ? ResponseEntity.ok(article) : ResponseEntity.notFound().build();
    }
}
