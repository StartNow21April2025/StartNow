package com.startnow.blog.controller;

import com.startnow.blog.model.tablemodel.Agent;
import com.startnow.blog.service.AgentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Map;

@RestController
@RequestMapping("/api/agents")
public class AgentController {

    @Autowired
    private AgentService agentService;

    @PostMapping("/createAgent")
    @Operation(summary = "Create a Agent in table")
    public ResponseEntity<Void> create(@RequestBody Agent agent) {
        agentService.createAgent(agent.getAgentId(), agent.getAgentName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAgent")
    @Operation(summary = "Read a Agent in table")
    public ResponseEntity<Agent> read(@RequestParam int agentId) {
        return ResponseEntity.ok(agentService.getAgent(agentId));
    }

    @PutMapping("/updateAgent")
    @Operation(summary = "Update a Agent in table")
    public ResponseEntity<Void> update(@RequestBody Agent agent) {
        agentService.updateAgent(agent.getAgentId(), agent.getAgentName());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteAgent")
    @Operation(summary = "Delete a Agent in table")
    public ResponseEntity<Void> delete(@RequestParam int agentId) {
        agentService.deleteAgent(agentId);
        return ResponseEntity.ok().build();
    }
}

