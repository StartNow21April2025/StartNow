package com.startnow.blog.service;

import com.startnow.blog.model.Agent;

import java.util.List;

public interface AgentServiceInterface {

    public Agent createAgent(Agent agent);

    public Agent updateAgent(Integer agentId, Agent updatedAgent);

    public Agent getAgentById(Integer agentId);

    public void deleteAgent(Integer agentId);

    public List<Agent> getAllAgents();

}
