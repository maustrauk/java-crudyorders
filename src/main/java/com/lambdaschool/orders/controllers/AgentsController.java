package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Agent;
import com.lambdaschool.orders.services.AgentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agents")
public class AgentsController {
    @Autowired
    private AgentsService agentsService;

    @GetMapping(value = "/agent/{agentId}", produces = "application/json")
    public ResponseEntity<?> getAgentById (@PathVariable Long agentId) {
        Agent a = agentsService.findAgentById(agentId);

        return new ResponseEntity<>(a, HttpStatus.OK);
    }
}
