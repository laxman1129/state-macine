package com.example.cds.statemachine.config;

import com.example.cds.statemachine.registry.WorkflowRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class WorkflowLoader {

    private final WorkflowProperties properties;
    private final WorkflowRegistry registry;

    public WorkflowLoader(WorkflowProperties properties, WorkflowRegistry registry) {
        this.properties = properties;
        this.registry = registry;
    }

    @PostConstruct
    public void load() {
        properties.definitions()
                .forEach((workflowName, definition) -> {
                    validate(workflowName, definition);
                    registry.register(workflowName, definition);
                });
    }

    private void validate(String workflowName, WorkflowDefinition definition) {
        validateInitialState(workflowName, definition);
        validateStates(workflowName, definition);
        validateDuplicateTransitions(workflowName, definition);
    }

    private void validateInitialState(String workflowName, WorkflowDefinition definition) {
        if (!definition.states().contains(definition.initialState())) {
            throw new IllegalStateException("""
                    Workflow [%s]
                    contains invalid initial state [%s]
                    """.formatted(workflowName, definition.initialState()));
        }
    }

    private void validateStates(String workflowName, WorkflowDefinition definition) {
        for (TransitionDefinition transition : definition.transitions()) {
            if (!definition.states().contains(transition.source())) {
                throw new IllegalStateException("""
                        Workflow [%s]
                        contains invalid source state [%s]
                        """.formatted(workflowName, transition.source()));
            }

            if (!definition.states().contains(transition.target())) {
                throw new IllegalStateException("""
                        Workflow [%s]
                        contains invalid target state [%s]
                        """.formatted(workflowName, transition.target()));
            }
        }
    }

    private void validateDuplicateTransitions(String workflowName, WorkflowDefinition definition) {
        Set<String> transitionKeys = new HashSet<>();
        for (TransitionDefinition transition : definition.transitions()) {
            String key = transition.source() + "::" + transition.event();
            if (!transitionKeys.add(key)) {
                throw new IllegalStateException("""
                        Duplicate transition found in workflow [%s]
                        for source [%s] and event [%s]
                        """.formatted(workflowName, transition.source(), transition.event()));
            }
        }
    }
}