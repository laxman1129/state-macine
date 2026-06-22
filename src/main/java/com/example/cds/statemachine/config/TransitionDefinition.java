package com.example.cds.statemachine.config;

public record TransitionDefinition(
                                String workflowName,
                                String source,
                                String target,
                                String event,
                                String guard,
                                String action) {
}