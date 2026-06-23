package com.example.cds.statemachine.repository;

import com.example.cds.statemachine.model.WorkflowInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowInstanceRepository extends JpaRepository<WorkflowInstance, Long> {

    WorkflowInstance findByWorkflowNameAndEntityId(String workflowName, Long entityId);
}