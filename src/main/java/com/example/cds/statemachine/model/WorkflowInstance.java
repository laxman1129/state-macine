package com.example.cds.statemachine.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public record WorkflowInstance(@Id @GeneratedValue Long id,
                               String entityType,
                               Long entityId,
                               String workflowName,
                               Integer workflowVersion,
                               String currentState) {
}