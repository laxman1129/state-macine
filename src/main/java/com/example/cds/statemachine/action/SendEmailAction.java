package com.example.cds.statemachine.action;

import com.example.cds.statemachine.engine.WorkflowContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("sendEmailAction")
public class SendEmailAction implements WorkflowAction {

    private static final Logger log = LoggerFactory.getLogger(SendEmailAction.class);

    @Override
    public void execute(WorkflowContext context) {
        log.info("Sending email for entity {}", context.entityId());
    }
}