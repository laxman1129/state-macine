package com.example.cds.statemachine.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "workflow")
public record WorkflowProperties(Map<String, WorkflowDefinition> definitions) {
}