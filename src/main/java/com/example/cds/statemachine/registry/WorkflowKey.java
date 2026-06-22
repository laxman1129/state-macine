package com.example.cds.statemachine.registry;

public record WorkflowKey(String workflowName,
                          Integer version) {
}