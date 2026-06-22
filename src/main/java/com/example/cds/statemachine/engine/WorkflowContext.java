package com.example.cds.statemachine.engine;

import java.util.Map;

public record WorkflowContext(String entityType,
                              Long entityId,
                              String workflowName,
                              String currentState,
                              String event,
                              String performedBy,
                              Map<String, Object> data) {
    public Object get(String key) {
        return data == null ? null : data.get(key);
    }

    public <T> T get(String key, Class<T> type) {
        Object value = get(key);

        if (value == null) {
            return null;
        }

        return type.cast(value);
    }
}