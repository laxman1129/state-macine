package com.example.cds.statemachine.guard;

import com.example.cds.statemachine.engine.WorkflowContext;

public interface WorkflowGuard {
    boolean evaluate(WorkflowContext context);
}