package com.example.cds.statemachine.config;

public record TransitionDefinition(String source,
                                   String target,
                                   String event,
                                   String guard,
                                   String action) {
}