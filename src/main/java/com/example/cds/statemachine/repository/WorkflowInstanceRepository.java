package com.example.cds.statemachine.repository;

import com.example.cds.statemachine.model.WorkflowInstance;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class WorkflowInstanceRepository {
    private final Map<String, WorkflowInstance> store = new ConcurrentHashMap<>();

    public WorkflowInstance findByEntityTypeAndEntityId(String workflowName, Long entityId) {

        return store.getOrDefault(workflowName + ":" + entityId,
                new WorkflowInstance(null, null, entityId, workflowName, null, "DRAFT"));
    }

    public WorkflowInstance save(WorkflowInstance instance) {
        Long id = instance.entityId();
        if (id == null) {
            id = Math.random() > 0.5 ? 1L : 2L;
        }
        store.putIfAbsent(instance.workflowName() + ":" + id, instance);
        return instance;
    }
}
