package com.lambdaschool.crudyorders.controllers;

import com.lambdaschool.crudyorders.models.Agent;
import com.lambdaschool.crudyorders.services.AgentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping(value = "/unassigned/{agentcode}")
    public ResponseEntity<?> deleteUnassignedAgent(@PathVariable long agentcode) {
        agentsService.deleteUnassigned(agentcode);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
