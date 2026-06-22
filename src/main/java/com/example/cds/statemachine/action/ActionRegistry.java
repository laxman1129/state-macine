package com.example.cds.statemachine.action;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ActionRegistry {

    private final Map<String, WorkflowAction> actions;

    public ActionRegistry(Map<String, WorkflowAction> actions) {
        this.actions = actions;
    }

    public WorkflowAction get(String actionName) {

        WorkflowAction action = actions.get(actionName);

        if (action == null) {
            throw new IllegalArgumentException("Action not found: " + actionName);
        }

        return action;
    }
}