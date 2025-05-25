package com.startnow.blog.service_tests;

import com.startnow.blog.entity.AgentEntity;
import com.startnow.blog.exception.ResourceNotFoundException;
import com.startnow.blog.model.Agent;
import com.startnow.blog.repository.IAgentRepository;
import com.startnow.blog.service.AgentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgentServiceTests {

    @InjectMocks
    private AgentService agentService;

    @Mock
    private IAgentRepository agentRepository;

    private Agent agent;
    private AgentEntity agentEntity;

    @BeforeEach
    void setUp() {
        agent = Agent.builder().agentId(1).agentName("Jett").status("active").build();

        agentEntity = AgentEntity.builder().agentId(1).agentName("Jett").status("active").build();
    }

    @Test
    @DisplayName("Should create agent successfully")
    void createAgent_Success() {
        when(agentRepository.save(any())).thenReturn(agentEntity);

        Agent result = agentService.createAgent(agent);

        assertNotNull(result);
        assertEquals(agent.getAgentId(), result.getAgentId());
        assertEquals(agent.getAgentName(), result.getAgentName());
        assertEquals(agent.getStatus(), result.getStatus());
        verify(agentRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Should return agent when found by ID")
    void getAgent_Found() {
        when(agentRepository.findById(any())).thenReturn(Optional.ofNullable(agentEntity));

        Agent result = agentService.getAgentById(1);

        assertAll(() -> assertEquals(1, result.getAgentId()),
                () -> assertEquals("active", result.getStatus()),
                () -> assertEquals("Jett", result.getAgentName()));
        verify(agentRepository, times(1)).findById(any());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when agent not found by ID")
    void getAgent_NotFound() {
        when(agentRepository.findById(any())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class,
                () -> agentService.updateAgent(999, agent));

        assertEquals("Agent not found with id: 999", exception.getMessage());
    }

    @Test
    @DisplayName("Should update agent successfully")
    void updateAgent_Success() {
        Agent updateAgent = Agent.builder().agentId(1).agentName("Sage").status("active").build();
        AgentEntity updateAgentEntity =
                AgentEntity.builder().agentId(1).agentName("Sage").status("active").build();

        when(agentRepository.findById(any())).thenReturn(Optional.of(agentEntity));
        when(agentRepository.save(any())).thenReturn(updateAgentEntity);

        agentService.updateAgent(1, updateAgent);

        verify(agentRepository, times(1)).findById(any());
        verify(agentRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Should delete agent successfully")
    void deleteAgent_Success() {

        // Mock both findById and delete
        when(agentRepository.findById(1)).thenReturn(Optional.of(agentEntity));
        doNothing().when(agentRepository).delete(any());
        // When
        agentService.deleteAgent(1);
        // Then
        verify(agentRepository, times(1)).findById(1);
        verify(agentRepository, times(1)).delete(any());
    }
}
