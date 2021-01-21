package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Agent;
import com.lambdaschool.crudyorders.repositories.AgentsRepository;
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

    @Transactional
    @Override
    public void deleteUnassigned(long id) {
        Agent currentAgent = agentsrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agent " + id + " not found"));
        if(currentAgent.getCustomers().size() == 0) {
            agentsrepos.deleteById(id);
        } else {
            throw new EntityNotFoundException("Found A Customer For Agent " + id);
        }
    }
}
