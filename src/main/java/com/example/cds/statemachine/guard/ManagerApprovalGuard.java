package com.example.cds.statemachine.guard;

import com.example.cds.statemachine.engine.WorkflowContext;
import org.springframework.stereotype.Component;

@Component("managerApprovalGuard")
public class ManagerApprovalGuard implements WorkflowGuard {

    @Override
    public boolean evaluate(WorkflowContext context) {
        String role = context.get("role", String.class);
        return "MANAGER".equals(role);
    }
}