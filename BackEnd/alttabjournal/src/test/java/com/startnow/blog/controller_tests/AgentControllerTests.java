package com.startnow.blog.controller_tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.startnow.blog.controller.AgentController;
import com.startnow.blog.model.tablemodel.Agent;
import com.startnow.blog.service.AgentService;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

class AgentControllerTests {

    @Mock
    private AgentService agentService;

    @InjectMocks
    private AgentController agentController;

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
    @DisplayName("Should create agent and return 200")
    void createAgent_Returns200() {
        Agent agent = new Agent(1, "Phoenix");
        doNothing().when(agentService).createAgent(agent.getAgentId(), agent.getAgentName());

        ResponseEntity<Void> response = agentController.create(agent);

        assertEquals(200, response.getStatusCode().value());
        verify(agentService, times(1)).createAgent(agent.getAgentId(), agent.getAgentName());
    }

    @Test
    @DisplayName("Should return agent when found by ID")
    void getAgentById_Found() {
        Agent mockAgent = new Agent(1, "Jett");
        when(agentService.getAgent(1)).thenReturn(Optional.of(mockAgent));

        ResponseEntity<Agent> response = agentController.read(1);

        assertAll(() -> assertEquals(200, response.getStatusCode().value()),
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(1, response.getBody().getAgentId()),
                () -> assertEquals("Jett", response.getBody().getAgentName()));
    }

    @Test
    @DisplayName("Should return 404 when agent not found by ID")
    void getAgentById_NotFound() {
        when(agentService.getAgent(999)).thenReturn(Optional.empty());

        ResponseEntity<Agent> response = agentController.read(999);

        assertEquals(404, response.getStatusCode().value());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Should update agent and return 200")
    void updateAgent_Returns200() {
        Agent updatedAgent = new Agent(1, "Sage");
        doNothing().when(agentService).updateAgent(updatedAgent.getAgentId(),
                updatedAgent.getAgentName());

        ResponseEntity<Void> response = agentController.update(1, updatedAgent);

        assertEquals(200, response.getStatusCode().value());
        verify(agentService, times(1)).updateAgent(updatedAgent.getAgentId(),
                updatedAgent.getAgentName());
    }

    @Test
    @DisplayName("Should delete agent and return 200")
    void deleteAgent_Returns200() {
        doNothing().when(agentService).deleteAgent(1);

        ResponseEntity<Void> response = agentController.delete(1);

        assertEquals(200, response.getStatusCode().value());
        verify(agentService, times(1)).deleteAgent(1);
    }

    @Test
    @DisplayName("Should handle null agent on create gracefully")
    void createAgent_NullAgent() {
        ResponseEntity<Void> response = agentController.create(null);
        assertEquals(400, response.getStatusCode().value());
    }
}
