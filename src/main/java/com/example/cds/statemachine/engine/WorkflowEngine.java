package com.example.cds.statemachine.engine;

import com.example.cds.statemachine.model.WorkflowInstance;

public interface WorkflowEngine {

    WorkflowInstance fireEvent(String workflowName,
                               Long entityId,
                               String entityType,
                               String event,
                               WorkflowContext context);
}