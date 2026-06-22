package com.example.cds.statemachine.engine;

import com.example.cds.statemachine.config.TransitionDefinition;
import com.example.cds.statemachine.config.WorkflowDefinition;
import org.springframework.stereotype.Component;

@Component
public class TransitionResolver {

    public TransitionDefinition resolve(WorkflowDefinition workflow,
                                        String currentState,
                                        String event) {

        return workflow.transitions()
                .stream()
                .filter(t ->
                        t.source().equals(currentState)
                                && t.event().equals(event)
                )
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException(
                                String.format(
                                        "No transition found for state [%s] and event [%s]",
                                        currentState,
                                        event
                                )
                        )
                );
    }
}