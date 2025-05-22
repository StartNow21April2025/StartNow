package com.startnow.blog.service_tests;

import com.startnow.blog.exception.AgentNotFoundException;
import com.startnow.blog.model.tablemodel.Agent;
import com.startnow.blog.service.AgentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AgentServiceTests {

    @Mock
    private DynamoDbClient dynamoDbClient;

    @InjectMocks
    private AgentService agentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAgent() {
        PutItemResponse response = PutItemResponse.builder().build(); // Mock response

        when(dynamoDbClient.putItem(any(PutItemRequest.class))).thenReturn(response);
        agentService.createAgent(1, "Phoenix");

        verify(dynamoDbClient, times(1)).putItem(any(PutItemRequest.class));
    }

    @Test
    void testGetAgent() {
        Map<String, AttributeValue> item = Map.of(
                "agentId", AttributeValue.fromN("1"),
                "agentName", AttributeValue.fromS("Jett")
        );
        GetItemResponse response = GetItemResponse.builder().item(item).build();

        when(dynamoDbClient.getItem(any(GetItemRequest.class))).thenReturn(response);

        Optional<Agent> agentOpt = agentService.getAgent(1);
        assertTrue(agentOpt.isPresent());
        Agent agent = agentOpt.get();
        assertEquals(1, agent.getAgentId());
        assertEquals("Jett", agent.getAgentName());
    }

    @Test
    void testGetAgentNotFound() {
        when(dynamoDbClient.getItem(any(GetItemRequest.class))).thenReturn(GetItemResponse.builder().build());

        Optional<Agent> agentOpt = agentService.getAgent(999);
        assertFalse(agentOpt.isPresent());
    }

    @Test
    void testUpdateAgent() {
        PutItemResponse response = PutItemResponse.builder().build(); // Mock response

        when(dynamoDbClient.putItem(any(PutItemRequest.class))).thenReturn(response);
        agentService.updateAgent(1, "Sage");

        verify(dynamoDbClient, times(1)).putItem(any(PutItemRequest.class));
    }

    @Test
    void testUpdateAgentDoesNotExist() {
        doThrow(ConditionalCheckFailedException.builder().build())
                .when(dynamoDbClient).putItem(any(PutItemRequest.class));

        Exception exception = assertThrows(AgentNotFoundException.class, () -> agentService.updateAgent(999, "NonExistent"));

        assertEquals("Agent with ID 999 does not exist.", exception.getMessage());
    }

    @Test
    void testDeleteAgent() {
        DeleteItemResponse response = DeleteItemResponse.builder().build(); // Mock response

        when(dynamoDbClient.deleteItem(any(DeleteItemRequest.class))).thenReturn(response);
        agentService.deleteAgent(1);

        verify(dynamoDbClient, times(1)).deleteItem(any(DeleteItemRequest.class));
    }
}