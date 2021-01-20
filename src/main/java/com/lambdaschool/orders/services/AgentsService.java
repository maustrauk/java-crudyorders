package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Agent;

public interface AgentsService {
    Agent save(Agent agent);
    Agent findAgentById(long id);
}
