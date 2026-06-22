package com.example.cds.statemachine.guard;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GuardRegistry {

    private final Map<String, WorkflowGuard> guards;

    public GuardRegistry(Map<String, WorkflowGuard> guards) {
        this.guards = guards;
    }

    public WorkflowGuard get(String guardName) {
        WorkflowGuard guard = guards.get(guardName);
        if (guard == null) {
            throw new IllegalArgumentException("Guard not found: " + guardName);
        }
        return guard;
    }
}