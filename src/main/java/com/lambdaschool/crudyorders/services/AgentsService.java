package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Agent;

public interface AgentsService {
    Agent save(Agent agent);
    Agent findAgentById(long id);
    void deleteUnassigned(long id);
}
