package com.example.cds.statemachine.model;

public record Transition(String source,
                         String target,
                         String event,
                         String guard,
                         String action) {


}