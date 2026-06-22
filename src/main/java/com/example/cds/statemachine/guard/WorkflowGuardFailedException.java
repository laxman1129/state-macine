package com.example.cds.statemachine.guard;

public class WorkflowGuardFailedException extends RuntimeException {

    public WorkflowGuardFailedException(String guardName,
                                        String workflowName,
                                        Long entityId) {
        super(
                String.format(
                        "Guard [%s] failed for workflow [%s], entity [%s]",
                        guardName,
                        workflowName,
                        entityId
                )
        );
    }
}