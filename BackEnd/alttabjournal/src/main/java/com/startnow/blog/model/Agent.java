package com.startnow.blog.model;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Agent {
    private Integer agentId;
    @NotBlank(message = "Agent Name is required")
    private String name;
    private String title;
    private String quote;
    private String description;
    private String imageUrl;
    private String strengths;
    private String weaknesses;
    private String status;
    private String authorName;
    @Hidden
    private String createdAt;
    @Hidden
    private String updatedAt;
}

