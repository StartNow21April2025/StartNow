package com.startnow.blog.controller;

import com.startnow.blog.model.Agent;
import com.startnow.blog.service.AgentServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** REST controller for managing Agent entities. */
@Validated
@RestController
@RequestMapping("/api/agents")
@Tag(name = "Agent Controller", description = "APIs for managing agents")
public class AgentController {

    private final AgentServiceInterface agentService;

    /**
     * Constructs an AgentController with the given AgentService.
     *
     * @param agentService the service to handle agentName operations
     */
    public AgentController(AgentServiceInterface agentService) {
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
    public ResponseEntity<Agent> create(@Valid @RequestBody Agent agent) {
        if (agent == null) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(agentService.createAgent(agent), HttpStatus.CREATED);
    }

    /**
     * Retrieves an Agent by its ID.
     *
     * @param agentId the ID of the Agent to retrieve
     * @return the Agent if found, or HTTP 404 if not found
     */
    @GetMapping("/{agentId}")
    @Operation(summary = "Read an Agent in table")
    public ResponseEntity<Agent> get(@PathVariable Integer agentId) {
        return ResponseEntity.ok(agentService.getAgentById(agentId));

    }

    /**
     * Retrieves all Agents.
     *
     * @return the List of Agents if found, or HTTP 404 if not found
     */
    @GetMapping("all")
    @Operation(summary = "Get all Agent in table")
    public ResponseEntity<List<Agent>> getAllAgents() {
        return ResponseEntity.ok(agentService.getAllAgents());

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
    public ResponseEntity<Agent> update(@PathVariable Integer agentId,
            @Valid @RequestBody Agent agent) {
        return ResponseEntity.ok(agentService.updateAgent(agentId, agent));
    }

    /**
     * Deletes an Agent by its ID.
     *
     * @param agentId the ID of the Agent to delete
     * @return HTTP 200 OK if deleted successfully
     */
    @DeleteMapping("/{agentId}")
    @Operation(summary = "Delete an Agent in table")
    public ResponseEntity<Void> delete(@PathVariable Integer agentId) {
        agentService.deleteAgent(agentId);
        return ResponseEntity.noContent().build();
    }
}
