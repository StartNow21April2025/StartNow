package com.startnow.blog.util;

import com.startnow.blog.entity.AgentEntity;
import com.startnow.blog.exception.ValidationException;
import com.startnow.blog.model.Agent;

import java.util.ArrayList;
import java.util.List;

public final class AgentUtil {
    // Private constructor to prevent instantiation
    private AgentUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static Agent convertToAgent(AgentEntity entity) {
        if (entity == null) {
            return null;
        }

        return Agent.builder().agentId(entity.getAgentId()).name(entity.getName())
                .title(entity.getTitle()).quote(entity.getQuote())
                .description(entity.getDescription()).imageUrl(entity.getImageUrl())
                .strengths(entity.getStrengths()).weaknesses(entity.getWeaknesses())
                .status(entity.getStatus()).authorName(entity.getAuthorName())
                .createdAt(entity.getCreatedAt()).updatedAt(entity.getUpdatedAt()).build();
    }

    public static AgentEntity convertToAgentEntity(Agent agent) {
        if (agent == null) {
            return null;
        }

        return AgentEntity.builder().agentId(agent.getAgentId()).name(agent.getName())
                .title(agent.getTitle()).quote(agent.getQuote()).description(agent.getDescription())
                .imageUrl(agent.getImageUrl()).strengths(agent.getStrengths())
                .weaknesses(agent.getWeaknesses()).status(agent.getStatus())
                .authorName(agent.getAuthorName()).createdAt(agent.getCreatedAt())
                .updatedAt(agent.getUpdatedAt()).build();
    }

    public static void validateAgent(AgentEntity agent) {
        List<String> errors = new ArrayList<>();

        if (agent == null) {
            throw new ValidationException("Agent cannot be null");
        }

        if (agent.getName() == null || agent.getName().trim().isEmpty()) {
            errors.add("Agent name is required");
        }

        if (agent.getStatus() == null || agent.getStatus().trim().isEmpty()) {
            errors.add("Status is required");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException("Agent validation failed: " + String.join(", ", errors));
        }
    }
}

