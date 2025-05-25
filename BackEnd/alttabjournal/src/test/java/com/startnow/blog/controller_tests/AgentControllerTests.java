package com.startnow.blog.controller_tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.startnow.blog.controller.AgentController;
import com.startnow.blog.exception_handler.GlobalExceptionHandler;
import com.startnow.blog.exception.ResourceNotFoundException;
import com.startnow.blog.exception.ServiceException;
import com.startnow.blog.model.Agent;
import com.startnow.blog.service.AgentService;
import com.startnow.blog.service.AgentServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AgentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private AgentController agentController;

    @Mock
    private AgentServiceInterface agentService;

    private Agent testAgent;

    @BeforeEach
    void setUp() {
        testAgent =
                Agent.builder().agentId(1).agentName("Test Agent").description("Test Description")
                        .status("ACTIVE").createdAt(LocalDateTime.now().toString()).build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(agentController)
                .setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    void createAgent() throws Exception {
        // Given
        Agent testAgent = new Agent();
        testAgent.setAgentId(123);
        testAgent.setAgentName("Test Agent");
        testAgent.setStatus("ACTIVE");

        when(agentService.createAgent(any(Agent.class))).thenReturn(testAgent);

        // When/Then
        mockMvc.perform(post("/api/agents").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testAgent)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.agentId").value(testAgent.getAgentId()))
                .andExpect(jsonPath("$.agentName").value(testAgent.getAgentName()))
                .andExpect(jsonPath("$.status").value(testAgent.getStatus())).andDo(print());

        // Verify that service was called
        verify(agentService, times(1)).createAgent(any(Agent.class));
    }

    @Test
    void createAgent_WithInvalidData_ShouldReturnBadRequest() throws Exception {

        // When/Then
        mockMvc.perform(post("/api/agents").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(null))).andExpect(status().isBadRequest());
    }

    @Test
    void createAgent_WhenServiceThrowsException_ShouldReturnInternalServerError() throws Exception {
        // Given
        ServiceException serviceException = new ServiceException("Failed to create agent");
        when(agentService.createAgent(any(Agent.class))).thenThrow(serviceException);

        // When/Then
        mockMvc.perform(post("/api/agents").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testAgent)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").exists()).andDo(print());

        verify(agentService, times(1)).createAgent(any(Agent.class));
    }


    @Test
    void getAgentById_ShouldReturnAgent() throws Exception {
        when(agentService.getAgentById(1)).thenReturn(testAgent);

        mockMvc.perform(get("/api/agents/{id}", 1)).andExpect(status().isOk())
                .andExpect(jsonPath("$.agentId").value(testAgent.getAgentId()))
                .andExpect(jsonPath("$.agentName").value(testAgent.getAgentName()));
    }

    @Test
    void getAgentById_WhenNotFound_ShouldReturn404() throws Exception {
        when(agentService.getAgentById(999))
                .thenThrow(new ResourceNotFoundException("Agent not found with id: 999"));

        mockMvc.perform(get("/api/agents/{id}", 999)).andExpect(status().isNotFound());
    }

    @Test
    void getAllAgents_ShouldReturnList() throws Exception {
        List<Agent> agents = new ArrayList<>();
        agents.add(testAgent);
        when(agentService.getAllAgents()).thenReturn(agents);

        mockMvc.perform(get("/api/agents/all")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].agentId").value(testAgent.getAgentId()))
                .andExpect(jsonPath("$[0].agentName").value(testAgent.getAgentName()));
    }


    @Test
    void updateAgent_ShouldReturnUpdatedAgent() throws Exception {
        Agent updatedAgent =
                Agent.builder().agentId(1).agentName("Updated Agent").status("INACTIVE").build();

        when(agentService.updateAgent(eq(1), any(Agent.class))).thenReturn(updatedAgent);

        mockMvc.perform(put("/api/agents/{id}", 1).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedAgent))).andExpect(status().isOk())
                .andExpect(jsonPath("$.agentName").value("Updated Agent"))
                .andExpect(jsonPath("$.status").value("INACTIVE"));
    }

    @Test
    void deleteAgent_ShouldReturn204() throws Exception {
        doNothing().when(agentService).deleteAgent(1);

        mockMvc.perform(delete("/api/agents/{id}", 1)).andExpect(status().isNoContent());
    }

    @Test
    void updateAgent_WhenNotFound_ShouldReturn404() throws Exception {
        Agent updateRequest = Agent.builder().agentName("Updated Name").status("active").build();

        when(agentService.updateAgent(eq(999), any(Agent.class)))
                .thenThrow(new ResourceNotFoundException("Agent not found with id: 999"));

        mockMvc.perform(put("/api/agents/{id}", 999).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Agent not found with id: 999"));
    }
}

