package com.example.cds.statemachine.action;

import com.example.cds.statemachine.engine.WorkflowContext;
import org.springframework.stereotype.Component;

@Component("publishEventAction")
public class PublishEventAction implements WorkflowAction {

    @Override
    public void execute(WorkflowContext context) {
        System.out.println("Publishing event for entity " + context.entityId());
    }
}