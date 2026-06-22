package com.example.cds.statemachine.action;

import com.example.cds.statemachine.config.TransitionDefinition;
import com.example.cds.statemachine.engine.WorkflowContext;
import org.springframework.stereotype.Component;

@Component
public class ActionExecutor {

    private final ActionRegistry actionRegistry;

    public ActionExecutor(ActionRegistry actionRegistry) {
        this.actionRegistry = actionRegistry;
    }

    public void execute(TransitionDefinition transition, WorkflowContext context) {
        if (transition.action() == null
                || transition.action().isBlank()) {
            return;
        }

        WorkflowAction action =
                actionRegistry.get(
                        transition.action()
                );

        action.execute(context);
    }
}