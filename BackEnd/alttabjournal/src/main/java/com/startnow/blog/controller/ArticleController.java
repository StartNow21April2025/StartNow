package com.startnow.blog.controller;

import com.startnow.blog.model.Article;
import com.startnow.blog.model.LatestArticle;
import com.startnow.blog.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")  // Allow frontend to call this API
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService service) {
        this.articleService = service;
    }

    @GetMapping("/articles")
    @Operation(summary = "Get all Articles")
    public List<LatestArticle> getArticles() {
        return articleService.getAllArticles();
    }

    // ✅ Fixed the incorrect API path (removed extra `/api`)
    @GetMapping("/articles/{slug}")
    @Operation(summary = "Get a specific Article")
    public ResponseEntity<Article> getArticle(@PathVariable String slug) {
        Article article = articleService.getArticleBySlug(slug);
        return article != null ? ResponseEntity.ok(article) : ResponseEntity.notFound().build();
    }
}