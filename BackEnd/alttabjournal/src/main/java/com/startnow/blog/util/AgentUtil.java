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

        return Agent.builder().agentId(entity.getAgentId()).agentName(entity.getAgentName())
                .agentTitle(entity.getAgentTitle()).tagLine(entity.getTagLine())
                .description(entity.getDescription()).status(entity.getStatus())
                .authorName(entity.getAuthorName()).createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt()).build();
    }

    public static AgentEntity convertToAgentEntity(Agent agent) {
        if (agent == null) {
            return null;
        }

        return AgentEntity.builder().agentId(agent.getAgentId()).agentName(agent.getAgentName())
                .agentTitle(agent.getAgentTitle()).tagLine(agent.getTagLine())
                .description(agent.getDescription()).status(agent.getStatus())
                .authorName(agent.getAuthorName()).updatedAt(agent.getUpdatedAt())
                .createdAt(agent.getCreatedAt()).build();
    }

    public static void validateAgent(AgentEntity agent) {
        List<String> errors = new ArrayList<>();

        if (agent == null) {
            throw new ValidationException("Agent cannot be null");
        }

        if (agent.getAgentName() == null || agent.getAgentName().trim().isEmpty()) {
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

