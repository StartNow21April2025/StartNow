package com.startnow.blog.model;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Agent {
    private Integer agentId;
    @NotBlank(message = "Agent Name is required")
    private String agentName;
    private String description;
    @NotBlank(message = "Status is required")
    private String status;
    private String authorName;
    @Hidden
    private String createdAt;
}

