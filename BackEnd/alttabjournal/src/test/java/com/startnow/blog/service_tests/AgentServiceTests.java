package com.startnow.blog.service_tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.startnow.blog.exception.AgentNotFoundException;
import com.startnow.blog.model.tablemodel.Agent;
import com.startnow.blog.service.AgentService;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

class AgentServiceTests {

    @Mock
    private DynamoDbClient dynamoDbClient;

    @InjectMocks
    private AgentService agentService;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (mocks != null) {
            mocks.close();
        }
    }

    @Test
    @DisplayName("Should create agent successfully")
    void createAgent_Success() {
        PutItemResponse response = PutItemResponse.builder().build();
        when(dynamoDbClient.putItem(any(PutItemRequest.class))).thenReturn(response);

        agentService.createAgent(1, "Phoenix");

        verify(dynamoDbClient, times(1)).putItem(any(PutItemRequest.class));
    }

    @Test
    @DisplayName("Should return agent when found by ID")
    void getAgent_Found() {
        Map<String, AttributeValue> item = Map.of("agentId", AttributeValue.fromN("1"), "agentName",
                AttributeValue.fromS("Jett"));
        GetItemResponse response = GetItemResponse.builder().item(item).build();
        when(dynamoDbClient.getItem(any(GetItemRequest.class))).thenReturn(response);

        Optional<Agent> agentOpt = agentService.getAgent(1);

        assertAll(() -> assertTrue(agentOpt.isPresent()),
                () -> assertEquals(1, agentOpt.get().getAgentId()),
                () -> assertEquals("Jett", agentOpt.get().getAgentName()));
    }

    @Test
    @DisplayName("Should return empty when agent not found by ID")
    void getAgent_NotFound() {
        when(dynamoDbClient.getItem(any(GetItemRequest.class)))
                .thenReturn(GetItemResponse.builder().build());

        Optional<Agent> agentOpt = agentService.getAgent(999);

        assertTrue(agentOpt.isEmpty());
    }

    @Test
    @DisplayName("Should update agent successfully")
    void updateAgent_Success() {
        PutItemResponse response = PutItemResponse.builder().build();
        when(dynamoDbClient.putItem(any(PutItemRequest.class))).thenReturn(response);

        agentService.updateAgent(1, "Sage");

        verify(dynamoDbClient, times(1)).putItem(any(PutItemRequest.class));
    }

    @Test
    @DisplayName("Should throw AgentNotFoundException when updating non-existent agent")
    void updateAgent_NotFound() {
        doThrow(ConditionalCheckFailedException.builder().build()).when(dynamoDbClient)
                .putItem(any(PutItemRequest.class));

        Exception exception = assertThrows(AgentNotFoundException.class,
                () -> agentService.updateAgent(999, "NonExistent"));

        assertEquals("Agent with ID 999 does not exist.", exception.getMessage());
    }

    @Test
    @DisplayName("Should delete agent successfully")
    void deleteAgent_Success() {
        DeleteItemResponse response = DeleteItemResponse.builder().build();
        when(dynamoDbClient.deleteItem(any(DeleteItemRequest.class))).thenReturn(response);

        agentService.deleteAgent(1);

        verify(dynamoDbClient, times(1)).deleteItem(any(DeleteItemRequest.class));
    }
}
