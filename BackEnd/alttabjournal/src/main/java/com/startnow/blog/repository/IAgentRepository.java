package com.startnow.blog.repository;

import com.startnow.blog.entity.AgentEntity;

import java.util.List;
import java.util.Optional;

// Agent Repository Interface
public interface IAgentRepository extends BaseRepository<AgentEntity, Integer> {

    public AgentEntity save(AgentEntity agentEntity);

    public void delete(Integer agentId);

    public Optional<AgentEntity> findById(Integer agentId);

    public List<AgentEntity> findAll();
}
