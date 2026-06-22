package com.example.cds.statemachine.api;

import java.util.Map;

public record WorkflowEventRequest(
        String entityType,
        Long entityId,
        String workflowName,
        String event,
        String performedBy,
        Map<String, Object> data) {
}