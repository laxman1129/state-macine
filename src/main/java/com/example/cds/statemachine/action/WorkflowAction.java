package com.example.cds.statemachine.action;


import com.example.cds.statemachine.engine.WorkflowContext;

public interface WorkflowAction {

    void execute(WorkflowContext context);

}