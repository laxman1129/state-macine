package com.example.cds.statemachine.registry;

import com.example.cds.statemachine.config.WorkflowDefinition;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WorkflowRegistry {
    private final Map<WorkflowKey, WorkflowDefinition> registry = new ConcurrentHashMap<>();

    public void register(String workflowName,
                         WorkflowDefinition definition) {
        registry.put(new WorkflowKey(workflowName, definition.version()), definition);
    }

    public WorkflowDefinition get(String workflowName, Integer version) {
        WorkflowDefinition definition = registry.get(new WorkflowKey(workflowName, version));
        if (definition == null) {
            throw new IllegalArgumentException("Workflow not found: " + workflowName + " version " + version);
        }
        return definition;
    }

}