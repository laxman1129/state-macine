package com.example.cds.statemachine.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class WorkflowInstance {
    @Id @GeneratedValue Long id;
    String entityType;
    Long entityId;
    String workflowName;
    Integer workflowVersion;
    String currentState;
    @Column(columnDefinition = "JSON") String data;

    public WorkflowInstance() {
    }

    public WorkflowInstance(Long id, String entityType, Long entityId, String workflowName, Integer workflowVersion, String currentState, String data) {
        this.id = id;
        this.entityType = entityType;
        this.entityId = entityId;
        this.workflowName = workflowName;
        this.workflowVersion = workflowVersion;
        this.currentState = currentState;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    public Integer getWorkflowVersion() {
        return workflowVersion;
    }

    public void setWorkflowVersion(Integer workflowVersion) {
        this.workflowVersion = workflowVersion;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}