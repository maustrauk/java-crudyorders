package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Agent;
import com.lambdaschool.orders.repositories.AgentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service(value = "agentsService")
public class AgentsServiceImpl implements AgentsService{

    @Autowired
    private AgentsRepository agentsrepos;

    @Transactional
    @Override
    public Agent save(Agent agent){
        return agentsrepos.save(agent);
    }

    @Override
    public Agent findAgentById(long id)
        throws EntityNotFoundException {
        return agentsrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agent " + id + " Not Found"));
    }
}
