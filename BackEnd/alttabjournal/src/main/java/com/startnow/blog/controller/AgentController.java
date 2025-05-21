package com.startnow.blog.controller;

import com.startnow.blog.model.tablemodel.Agent;
import com.startnow.blog.service.AgentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/** REST controller for managing Agent entities. */
@Validated
@RestController
@RequestMapping("/api/agents")
public class AgentController {

    private final AgentService agentService;

    /**
     * Constructs an AgentController with the given AgentService.
     *
     * @param agentService the service to handle agent operations
     */
    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    /**
     * Creates a new Agent.
     *
     * @param agent the Agent to create
     * @return HTTP 200 OK if created successfully
     */
    @PostMapping
    @Operation(summary = "Create an Agent in table")
    public ResponseEntity<Void> create(@Valid @RequestBody Agent agent) {
        if (agent == null) {
            return ResponseEntity.badRequest().build();
        }
        agentService.createAgent(agent.getAgentId(), agent.getAgentName());
        return ResponseEntity.ok().build();
    }

    /**
     * Retrieves an Agent by its ID.
     *
     * @param agentId the ID of the Agent to retrieve
     * @return the Agent if found, or HTTP 404 if not found
     */
    @GetMapping("/{agentId}")
    @Operation(summary = "Read an Agent in table")
    public ResponseEntity<Agent> read(@PathVariable int agentId) {
        return agentService.getAgent(agentId).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Updates an existing Agent.
     *
     * @param agentId the ID of the Agent to update
     * @param agent the Agent data to update
     * @return HTTP 200 OK if updated successfully
     */
    @PutMapping("/{agentId}")
    @Operation(summary = "Update an Agent in table")
    public ResponseEntity<Void> update(@PathVariable int agentId, @Valid @RequestBody Agent agent) {
        agentService.updateAgent(agentId, agent.getAgentName());
        return ResponseEntity.ok().build();
    }

    /**
     * Deletes an Agent by its ID.
     *
     * @param agentId the ID of the Agent to delete
     * @return HTTP 200 OK if deleted successfully
     */
    @DeleteMapping("/{agentId}")
    @Operation(summary = "Delete an Agent in table")
    public ResponseEntity<Void> delete(@PathVariable int agentId) {
        agentService.deleteAgent(agentId);
        return ResponseEntity.ok().build();
    }
}
