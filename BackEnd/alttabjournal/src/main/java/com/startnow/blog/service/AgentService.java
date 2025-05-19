package com.startnow.blog.service;

import com.startnow.blog.model.tablemodel.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.Map;

@Service
public class AgentService {

    @Autowired
    private DynamoDbClient dynamoDbClient;

    private final String tableName = "ValorantAgents";

    public void createAgent(int agentId, String agentName) {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("agentId", AttributeValue.fromN(String.valueOf(agentId)));
        item.put("agentName", AttributeValue.fromS(agentName));

        PutItemRequest request = PutItemRequest.builder()
                .tableName(tableName)
                .item(item)
                .build();

        dynamoDbClient.putItem(request);
    }

    public Agent getAgent(int agentId) {
        GetItemRequest request = GetItemRequest.builder()
                .tableName(tableName)
                .key(Map.of("agentId", AttributeValue.fromN(String.valueOf(agentId))))
                .build();

        Map<String, AttributeValue> item = dynamoDbClient.getItem(request).item();

        if (item == null || item.isEmpty()) {
            return new Agent(); // or throw custom NotFoundException
        }

        Agent agent = new Agent();
        agent.setAgentId(Integer.parseInt(item.get("agentId").n()));
        agent.setAgentName(item.get("agentName").s());

        return agent;
    }

    public void updateAgent(int agentId, String agentName) {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("agentId", AttributeValue.fromN(String.valueOf(agentId)));
        item.put("agentName", AttributeValue.fromS(agentName));

        PutItemRequest request = PutItemRequest.builder()
                .tableName(tableName)
                .item(item)
                .conditionExpression("attribute_exists(agentId)") // 👈 only update if item exists
                .build();

        try {
            dynamoDbClient.putItem(request);
        } catch (ConditionalCheckFailedException e) {
            throw new RuntimeException("Cannot update: Agent with ID " + agentId + " does not exist.");
        }
    }

    public void deleteAgent(int agentId) {
        DeleteItemRequest request = DeleteItemRequest.builder()
                .tableName(tableName)
                .key(Map.of("agentId", AttributeValue.fromN(String.valueOf(agentId))))
                .build();

        dynamoDbClient.deleteItem(request);
    }
}
