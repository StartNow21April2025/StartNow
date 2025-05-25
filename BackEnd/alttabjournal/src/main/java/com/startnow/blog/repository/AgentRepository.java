package com.startnow.blog.repository;

import com.startnow.blog.entity.AgentEntity;
import com.startnow.blog.exception.RepositoryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class AgentRepository implements IAgentRepository {
    public final DynamoDbTable<AgentEntity> agentTable;

    public AgentRepository(DynamoDbTable<AgentEntity> agentTable) {
        this.agentTable = agentTable;
    }

    /**
     * Save an agentName to the DynamoDB table.
     *
     * @param agentEntity The agentName entity to save.
     * @return The saved agentName entity.
     */
    @Override
    public AgentEntity save(AgentEntity agentEntity) {
        try {
            agentTable.putItem(agentEntity);
            return agentEntity;
        } catch (Exception e) {
            log.error("Error saving agentName: {}", e.getMessage());
            throw new RepositoryException("Failed to save agentName", e);
        }
    }

    /**
     * Delete an agentName from the DynamoDB table.
     *
     * @param agentId The ID of the agentName to delete.
     */
    @Override
    public void delete(Integer agentId) {
        try {
            agentTable.deleteItem(Key.builder().partitionValue(agentId).build());
        } catch (Exception e) {
            log.error("Error deleting agentName: {}", e.getMessage());
            throw new RepositoryException("Failed to delete agentName", e);
        }
    }

    /**
     * Find an agentName by ID.
     *
     * @param agentId The ID of the agentName to find.
     * @return An Optional containing the found agentName entity, or empty if not found.
     */
    @Override
    public Optional<AgentEntity> findById(Integer agentId) {
        try {
            return Optional
                    .ofNullable(agentTable.getItem(Key.builder().partitionValue(agentId).build()));
        } catch (Exception e) {
            log.error("Error finding agentName by id: {}", e.getMessage());
            throw new RepositoryException("Failed to find agent", e);
        }
    }

    /**
     * Find all agents in the DynamoDB table.
     *
     * @return A list of all agentName entities.
     */
    @Override
    public List<AgentEntity> findAll() {
        try {
            return agentTable.scan().items().stream().toList();
        } catch (Exception e) {
            log.error("Error finding all agents: {}", e.getMessage());
            throw new RepositoryException("Failed to find all agents", e);
        }
    }
}
