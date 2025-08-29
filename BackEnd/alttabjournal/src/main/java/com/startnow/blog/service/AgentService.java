package com.startnow.blog.service;

import com.startnow.blog.entity.AgentEntity;
import com.startnow.blog.exception.ResourceNotFoundException;
import com.startnow.blog.exception.ServiceException;
import com.startnow.blog.model.Agent;
import com.startnow.blog.repository.IAgentRepository;
import com.startnow.blog.util.AgentUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AgentService implements AgentServiceInterface {
    private final IAgentRepository agentRepository;

    @Override
    public Agent createAgent(Agent agent) {
        try {
            // If agentId is not set, you might want to generate one
            AgentEntity newAgent = AgentEntity.builder()
                    .agentId(agent.getAgentId() != null ? agent.getAgentId() : generateAgentId())
                    .name(agent.getName()).quote(agent.getQuote()).title(agent.getTitle())
                    .description(agent.getDescription()).imageUrl(agent.getImageUrl())
                    .strengths(agent.getStrengths()).weaknesses(agent.getWeaknesses())
                    .status(agent.getStatus()).createdAt(LocalDateTime.now().toString())
                    .authorName(agent.getAuthorName()).updatedAt(LocalDateTime.now().toString())
                    .build(); // Add update timestamp

            // You might want to add validation logic here
            AgentUtil.validateAgent(newAgent);
            return AgentUtil.convertToAgent(agentRepository.save(newAgent));
        } catch (Exception e) {
            log.error("Error creating agent: {}", e.getMessage());
            throw new ServiceException("Failed to create agent", e);
        }
    }

    @Override
    public Agent updateAgent(Integer agentId, Agent updatedAgent) {
        try {
            // Check if agent exists and throw exception if not found
            Agent existingAgent = getAgentById(agentId);

            // Create updated entity preserving original creation time and id
            AgentEntity updatedAgentEntity = AgentEntity.builder().agentId(agentId)
                    .name(updatedAgent.getName()).title(updatedAgent.getTitle())
                    .quote(updatedAgent.getQuote()).description(updatedAgent.getDescription())
                    .status(updatedAgent.getStatus()).createdAt(existingAgent.getCreatedAt())
                    .imageUrl(updatedAgent.getImageUrl()).strengths(updatedAgent.getStrengths())
                    .weaknesses(updatedAgent.getWeaknesses())
                    .updatedAt(LocalDateTime.now().toString())// Add update timestamp
                    .authorName(updatedAgent.getAuthorName()).build();

            // Validate the updated entity
            AgentUtil.validateAgent(updatedAgentEntity);

            return AgentUtil.convertToAgent(agentRepository.save(updatedAgentEntity));
        } catch (ResourceNotFoundException e) {
            log.error("Agent not found with id {}", agentId);
            throw e;
        } catch (Exception e) {
            log.error("Error updating agent with id {}: {}", agentId, e.getMessage());
            throw new ServiceException("Failed to update agent", e);
        }
    }

    @Override
    public Agent getAgentById(Integer agentId) {
        Optional<AgentEntity> agentEntity = agentRepository.findById(agentId);
        if (agentEntity.isEmpty()) {
            throw new ResourceNotFoundException("Agent not found with id: " + agentId);
        }
        return AgentUtil.convertToAgent(agentEntity.get());
    }

    @Override
    public List<Agent> getAllAgents() {
        try {
            return agentRepository.findAll().stream()
                    .sorted(Comparator.comparing(AgentEntity::getAgentId))
                    .map(AgentUtil::convertToAgent)
                    .filter(agent -> agent != null && agent.getStatus().equals("active"))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching all agents: {}", e.getMessage(), e);
            throw new ServiceException("Failed to fetch agents", e);
        }
    }

    @Override
    public void deleteAgent(Integer agentId) {
        try {
            // Check if agentName exists before deleting
            if (agentRepository.findById(agentId).isEmpty()) {
                throw new ResourceNotFoundException("Agent not found with id: " + agentId);
            }
            agentRepository.delete(agentId);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error deleting agentName with id {}: {}", agentId, e.getMessage());
            throw new ServiceException("Failed to delete agentName", e);
        }
    }

    private Integer generateAgentId() {
        // Implement your ID generation strategy
        // This is a simple example - you might want to use a more sophisticated approach
        return Math.toIntExact(System.currentTimeMillis());
    }
}
