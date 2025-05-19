package com.startnow.blog.controllertests;

import com.startnow.blog.controller.AgentController;
import com.startnow.blog.model.tablemodel.Agent;
import com.startnow.blog.service.AgentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AgentControllerTests {

    @Mock
    private AgentService agentService;

    @InjectMocks
    private AgentController agentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAgent() {
        Agent agent = new Agent(1, "Phoenix");

        doNothing().when(agentService).createAgent(agent.getAgentId(), agent.getAgentName());

        ResponseEntity<Void> response = agentController.create(agent);

        assertEquals(200, response.getStatusCodeValue());
        verify(agentService, times(1)).createAgent(agent.getAgentId(), agent.getAgentName());
    }

    @Test
    void testGetAgent() {
        Agent mockAgent = new Agent(1, "Jett");
        when(agentService.getAgent(1)).thenReturn(mockAgent);

        ResponseEntity<Agent> response = agentController.read(1);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Jett", response.getBody().getAgentName());
    }

    @Test
    void testGetAgentNotFound() {
        when(agentService.getAgent(999)).thenReturn(null);

        ResponseEntity<Agent> response = agentController.read(999);

        assertEquals(200, response.getStatusCodeValue()); // Consider using 404 in service for missing agents
        assertNull(response.getBody());
    }

    @Test
    void testUpdateAgent() {
        Agent updatedAgent = new Agent(1, "Sage");

        doNothing().when(agentService).updateAgent(updatedAgent.getAgentId(), updatedAgent.getAgentName());

        ResponseEntity<Void> response = agentController.update(updatedAgent);

        assertEquals(200, response.getStatusCodeValue());
        verify(agentService, times(1)).updateAgent(updatedAgent.getAgentId(), updatedAgent.getAgentName());
    }

    @Test
    void testDeleteAgent() {
        doNothing().when(agentService).deleteAgent(1);

        ResponseEntity<Void> response = agentController.delete(1);

        assertEquals(200, response.getStatusCodeValue());
        verify(agentService, times(1)).deleteAgent(1);
    }
}
