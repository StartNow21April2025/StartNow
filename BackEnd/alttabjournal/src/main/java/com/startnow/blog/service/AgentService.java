package com.startnow.blog.service;

import com.startnow.blog.exception.AgentNotFoundException;
import com.startnow.blog.model.tablemodel.Agent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class AgentService {

    private final DynamoDbClient dynamoDbClient;
    private final String tableName = "ValorantAgents";

    public AgentService(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    public void createAgent(int agentId, String agentName) {
        if (agentName == null || agentName.isBlank()) {
            throw new IllegalArgumentException("Agent name must not be null or empty");
        }
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("agentId", AttributeValue.fromN(String.valueOf(agentId)));
        item.put("agentName", AttributeValue.fromS(agentName));

        PutItemRequest request = PutItemRequest.builder()
                .tableName(tableName)
                .item(item)
                .build();

        try {
            dynamoDbClient.putItem(request);
            log.info("Created agent with id {}", agentId);
        } catch (DynamoDbException e) {
            log.error("Failed to create agent", e);
            throw new RuntimeException("Failed to create agent", e);
        }
    }

    public Optional<Agent> getAgent(int agentId) {
        GetItemRequest request = GetItemRequest.builder()
                .tableName(tableName)
                .key(Map.of("agentId", AttributeValue.fromN(String.valueOf(agentId))))
                .build();

        try {
            Map<String, AttributeValue> item = dynamoDbClient.getItem(request).item();
            if (item == null || item.isEmpty()) {
                return Optional.empty();
            }
            Agent agent = new Agent();
            agent.setAgentId(Integer.parseInt(item.get("agentId").n()));
            agent.setAgentName(item.get("agentName").s());
            return Optional.of(agent);
        } catch (DynamoDbException e) {
            log.error("Failed to get agent", e);
            throw new RuntimeException("Failed to get agent", e);
        }
    }

    public void updateAgent(int agentId, String agentName) {
        if (agentName == null || agentName.isBlank()) {
            throw new IllegalArgumentException("Agent name must not be null or empty");
        }
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("agentId", AttributeValue.fromN(String.valueOf(agentId)));
        item.put("agentName", AttributeValue.fromS(agentName));

        PutItemRequest request = PutItemRequest.builder()
                .tableName(tableName)
                .item(item)
                .conditionExpression("attribute_exists(agentId)")
                .build();

        try {
            dynamoDbClient.putItem(request);
            log.info("Updated agent with id {}", agentId);
        } catch (ConditionalCheckFailedException e) {
            log.warn("Agent with id {} not found for update", agentId);
            throw new AgentNotFoundException("Agent with ID " + agentId + " does not exist.");
        } catch (DynamoDbException e) {
            log.error("Failed to update agent", e);
            throw new RuntimeException("Failed to update agent", e);
        }
    }

    public void deleteAgent(int agentId) {
        DeleteItemRequest request = DeleteItemRequest.builder()
                .tableName(tableName)
                .key(Map.of("agentId", AttributeValue.fromN(String.valueOf(agentId))))
                .build();

        try {
            dynamoDbClient.deleteItem(request);
            log.info("Deleted agent with id {}", agentId);
        } catch (DynamoDbException e) {
            log.error("Failed to delete agent", e);
            throw new RuntimeException("Failed to delete agent", e);
        }
    }
}