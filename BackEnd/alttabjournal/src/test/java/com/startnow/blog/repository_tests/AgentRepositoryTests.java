package com.startnow.blog.repository_tests;

import com.startnow.blog.entity.AgentEntity;
import com.startnow.blog.exception.RepositoryException;
import com.startnow.blog.repository.AgentRepository;
import com.startnow.blog.repository.IAgentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.DeleteItemEnhancedResponse;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgentRepositoryTests {

    @Mock
    private DynamoDbTable<AgentEntity> agentTable;

    @Mock
    private PageIterable<AgentEntity> pageIterable;

    private IAgentRepository agentRepository;

    private static final Integer AGENT_ID = 123;
    private static final String AGENT_NAME = "Test Agent";

    @BeforeEach
    void setUp() {
        agentRepository = new AgentRepository(agentTable);
    }

    @Test
    void whenSaveAgent_thenReturnSavedAgent() {
        // Arrange
        AgentEntity agent = createSampleAgent();
        doNothing().when(agentTable).putItem(any(AgentEntity.class));

        // Act
        AgentEntity savedAgent = agentRepository.save(agent);

        // Assert
        assertNotNull(savedAgent);
        assertEquals(AGENT_ID, savedAgent.getAgentId());
        assertEquals(AGENT_NAME, savedAgent.getName());
        verify(agentTable).putItem(agent);
    }

    @Test
    void whenSaveAgentAndExceptionOccurs_thenThrowRepositoryException() {
        // Arrange
        AgentEntity agent = createSampleAgent();
        doThrow(new RuntimeException("DynamoDB error")).when(agentTable)
                .putItem(any(AgentEntity.class));

        // Act & Assert
        RepositoryException exception =
                assertThrows(RepositoryException.class, () -> agentRepository.save(agent));
        assertEquals("Failed to save agentName", exception.getMessage());
    }

    @Test
    void whenFindByIdAndAgentExists_thenReturnAgent() {
        // Arrange
        AgentEntity agent = createSampleAgent();
        when(agentTable.getItem(any(Key.class))).thenReturn(agent);

        // Act
        Optional<AgentEntity> result = agentRepository.findById(AGENT_ID);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(AGENT_ID, result.get().getAgentId());
        assertEquals(AGENT_NAME, result.get().getName());
    }

    @Test
    void whenFindByIdAndAgentDoesNotExist_thenReturnEmptyOptional() {
        // Arrange
        when(agentTable.getItem(any(Key.class))).thenReturn(null);

        // Act
        Optional<AgentEntity> result = agentRepository.findById(AGENT_ID);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void whenFindByIdAndExceptionOccurs_thenThrowRepositoryException() {
        // Arrange
        when(agentTable.getItem(any(Key.class))).thenThrow(new RuntimeException("DynamoDB error"));

        // Act & Assert
        RepositoryException exception =
                assertThrows(RepositoryException.class, () -> agentRepository.findById(AGENT_ID));
        assertEquals("Failed to find agent", exception.getMessage());
    }

    @Test
    void whenDeleteAgent_thenSuccessfullyDelete() {
        // Arrange
        when(agentTable.deleteItem(any(Key.class))).thenReturn(
                DeleteItemEnhancedResponse.builder(AgentEntity.class).build().attributes());

        // Act & Assert
        assertDoesNotThrow(() -> agentRepository.delete(AGENT_ID));
        verify(agentTable).deleteItem(any(Key.class));
    }

    @Test
    void whenDeleteAgentAndExceptionOccurs_thenThrowRepositoryException() {
        // Arrange
        when(agentTable.deleteItem(any(Key.class)))
                .thenThrow(new RuntimeException("DynamoDB error"));

        // Act & Assert
        RepositoryException exception =
                assertThrows(RepositoryException.class, () -> agentRepository.delete(AGENT_ID));
        assertEquals("Failed to delete agentName", exception.getMessage());
    }

    @Test
    void whenFindAll_thenReturnAllAgents() {
        // Arrange
        List<AgentEntity> agents = createSampleAgentList();
        when(agentTable.scan()).thenReturn(pageIterable);
        when(pageIterable.items()).thenReturn(() -> agents.iterator());

        // Act
        List<AgentEntity> result = agentRepository.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(AGENT_ID, result.get(0).getAgentId());
        assertEquals(AGENT_NAME, result.get(0).getName());
    }

    @Test
    void whenFindAllAndExceptionOccurs_thenThrowRepositoryException() {
        // Arrange
        when(agentTable.scan()).thenThrow(new RuntimeException("DynamoDB error"));

        // Act & Assert
        RepositoryException exception =
                assertThrows(RepositoryException.class, () -> agentRepository.findAll());
        assertEquals("Failed to find all agents", exception.getMessage());
    }

    private AgentEntity createSampleAgent() {
        return AgentEntity.builder().agentId(AGENT_ID).name(AGENT_NAME)
                .description("Test Description").createdAt("2024-01-01").updatedAt("2024-01-01")
                .build();
    }

    private List<AgentEntity> createSampleAgentList() {
        List<AgentEntity> agents = new ArrayList<>();
        agents.add(AgentEntity.builder().agentId(AGENT_ID).name(AGENT_NAME)
                .description("Test Description 1").createdAt("2024-01-01").updatedAt("2024-01-01")
                .build());
        agents.add(AgentEntity.builder().agentId(456).name("Test Agent 2")
                .description("Test Description 2").createdAt("2024-01-01").updatedAt("2024-01-01")
                .build());
        return agents;
    }
}

