package com.example.cds.statemachine.guard;

import com.example.cds.statemachine.config.TransitionDefinition;
import com.example.cds.statemachine.engine.WorkflowContext;
import org.springframework.stereotype.Component;

@Component
public class GuardEvaluator {

    private final GuardRegistry guardRegistry;

    public GuardEvaluator(GuardRegistry guardRegistry) {
        this.guardRegistry = guardRegistry;
    }

    public void evaluate(TransitionDefinition transition, WorkflowContext context) {

        if (transition.guard() == null || transition.guard().isBlank()) {
            return;
        }
        WorkflowGuard guard = guardRegistry.get(transition.guard());
        boolean allowed = guard.evaluate(context);
        if (!allowed) {
            throw new WorkflowGuardFailedException(transition.guard(), context.workflowName(), context.entityId());
        }
    }
}