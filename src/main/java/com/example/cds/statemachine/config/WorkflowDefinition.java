package com.example.cds.statemachine.config;

import java.util.List;

public record WorkflowDefinition(Integer version,
                                 String initialState,
                                 List<String> states,
                                 List<TransitionDefinition> transitions) {
}