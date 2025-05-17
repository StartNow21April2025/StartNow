// src/main/java/com/startnow/blog/controller/ArticleController.java
package com.startnow.blog.controller;

import com.startnow.blog.model.Article;
import com.startnow.blog.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")  // Allow frontend to call this API
public class ArticleController {

    private final ArticleService service;

    public ArticleController(ArticleService service) {
        this.service = service;
    }

    @GetMapping("/articles")
    public List<Article> getArticles() {
        return service.getAllArticles();
    }
}
