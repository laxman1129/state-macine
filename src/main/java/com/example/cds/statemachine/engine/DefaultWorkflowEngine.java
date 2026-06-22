package com.example.cds.statemachine.engine;

import com.example.cds.statemachine.action.ActionRegistry;
import com.example.cds.statemachine.config.TransitionDefinition;
import com.example.cds.statemachine.config.WorkflowDefinition;
import com.example.cds.statemachine.guard.GuardRegistry;
import com.example.cds.statemachine.model.WorkflowInstance;
import com.example.cds.statemachine.registry.WorkflowRegistry;
import com.example.cds.statemachine.repository.WorkflowInstanceRepository;
import org.springframework.stereotype.Service;

@Service
public class DefaultWorkflowEngine implements WorkflowEngine {

    public static final int VERSION = 1;
    private final WorkflowRegistry registry;
    private final WorkflowInstanceRepository repo;
    private final ActionRegistry actionRegistry;
    private final GuardRegistry guardRegistry;
    private final TransitionResolver transitionResolver;

    public DefaultWorkflowEngine(WorkflowRegistry registry,
                                 WorkflowInstanceRepository repo,
                                 ActionRegistry actionRegistry,
                                 GuardRegistry guardRegistry,
                                 TransitionResolver transitionResolver) {
        this.registry = registry;
        this.repo = repo;
        this.actionRegistry = actionRegistry;
        this.guardRegistry = guardRegistry;
        this.transitionResolver = transitionResolver;
    }

      @Override
    public WorkflowInstance fireEvent(String workflowName,
                                      Long entityId,
                                      String entityType,
                                      String event,
                                      WorkflowContext context) {

        WorkflowInstance instance = repo.findByEntityTypeAndEntityId(workflowName, entityId);

        WorkflowDefinition def = registry.get(workflowName, VERSION);

        TransitionDefinition transition = transitionResolver.resolve(
            def, instance.currentState(), event
        );

        if (transition.guard() != null) {
            boolean ok = guardRegistry
                      .get(transition.guard())
                      .evaluate(context);

            if (!ok) throw new RuntimeException("Guard failed");
          }

        if (transition.action() != null) {
            actionRegistry
                      .get(transition.action())
                      .execute(context);
          }


        WorkflowInstance newInstance = new WorkflowInstance(
                instance.id(),
                instance.entityType(),
                entityId,
                instance.workflowName(),
                VERSION,
                transition.target()
          );

        return repo.save(newInstance);
      }
}