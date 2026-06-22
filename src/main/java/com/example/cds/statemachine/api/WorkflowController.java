package com.example.cds.statemachine.api;

import com.example.cds.statemachine.engine.WorkflowContext;
import com.example.cds.statemachine.engine.WorkflowEngine;
import com.example.cds.statemachine.model.WorkflowInstance;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workflow")
public class WorkflowController {

    private final WorkflowEngine engine;

    public WorkflowController(WorkflowEngine engine) {
        this.engine = engine;
    }

    @PostMapping("/event")
    public WorkflowInstance fire(@RequestBody WorkflowEventRequest req) {

        WorkflowContext ctx = new WorkflowContext(
                req.entityType(),
                req.entityId(),
                req.workflowName(),
                "",
                req.event(),
                req.performedBy(),
                req.data()
        );

        return engine.fireEvent(
                req.workflowName(),
                req.entityId(),
                req.entityType(),
                req.event(),
                ctx
        );
    }
}