# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

The State-Machine project is a Spring Boot application that implements configurable state machines where workflow definitions are managed through YAML configuration files. The system supports:

- REST API endpoints for firing workflow events
- Database persistence using MariaDB/H2 via JPA
- Extensible action and guard systems
- Multiple concurrent workflows (e.g., "station" and "supplier")

## Architecture

### Core Components

**1. Controller Layer**
- `WorkflowController`: REST API endpoint at `/workflow/event` for firing workflow events
- Handles HTTP requests with JSON payloads containing entity information, event names, and data

**2. Engine Layer**
- `DefaultWorkflowEngine`: Main state machine logic implementation
- Manages workflow instances and state transitions
- Executes guards and actions defined in workflows
- Persists workflow states to database via `WorkflowInstanceRepository`

**3. Configuration Layer**
- `WorkflowConfiguration`: Spring configuration for loading YAML workflows
- `WorkflowDefinition`: POJO representing workflow structure (states, transitions)
- Workflows are defined in `src/main/resources/application.yaml` as "station" and "supplier"

**4. Registry Systems**
- `ActionRegistry`: Manages custom actions like `SendEmailAction`, `PublishEventAction`
- `GuardRegistry`: Manages conditional checks like `ManagerApprovalGuard`
- `WorkflowRegistry`: Versioned workflow definitions keyed by name/version

**5. Data Models**
- `WorkflowInstance`: Database entity storing current state of each workflow instance
- `Transition`: Represents a single state transition with source, target, and event details

## Development Commands

### Build and Run
```bash
# Build the project using Maven
mvn clean install

# Run the application
mvn spring-boot:run

# Alternatively run directly as executable JAR
mvn package && java -jar target/*.jar
```

### Testing
```bash
# Run all tests
mvn test

# Run a specific test class
mvn test -Dtest=StatemachineApplicationTests

# Run integration tests (requires running application)
mvn spring-boot:test-runner
```

### Common Development Tasks

**1. Add new workflow**
- Create/update configuration in `src/main/resources/application.yaml`
- Define states and transitions for the new workflow
- Implement any custom actions/guards needed

**2. Create new action**
- Implement the `WorkflowAction` interface
- Register it with `@Component("actionName")` annotation
- Reference in YAML using `action: "actionName"`

**3. Add guard condition**
- Implement `WorkflowGuard` interface
- Register with `@Component("guardName")` annotation
- Reference in YAML transitions using `guard: "guardName"`

### Project Structure
```
src/main/java/com/example/cds/statemachine/
├── action/              # Custom actions (SendEmailAction, PublishEventAction)
├── api/                 # API models (WorkflowEventRequest) and controller
├── config/              # Workflow configuration classes
├── engine/              # State machine logic (DefaultWorkflowEngine, TransitionResolver)
├── guard/               # Conditional evaluation (ManagerApprovalGuard)
├── model/               # Data entities (WorkflowInstance, Transition)
├── registry/            # Component registries
└── repository/          # Database interfaces
src/main/resources/
└── application.yaml     # Workflow definitions
src/test/java/com/example/cds/statemachine/
└── StatemachineApplicationTests.java   # Integration tests
```

## API Usage

The main REST endpoint is:

**POST `/workflow/event`**

Request body (example from `sample.http`):
```json
{
  "workflowName": "station",
  "entityId": 99,
  "event": "SUBMIT",
  "performedBy": "user123",
  "data": {
    "itemName": "Sample Item",
    "itemDescription": "This is a sample item created for testing."
  }
}
```

**Response:** Returns the updated `WorkflowInstance` object with new state.

### Sample Workflow Definitions (from application.yaml)

**Station workflow:**
- States: DRAFT → PENDING_APPROVAL → ACTIVE → SUSPENDED
- Transitions: SUBMIT, APPROVE (requires managerApprovalGuard + sendEmailAction), SUSPEND

**Supplier workflow:**
- States: CREATED → VERIFIED → BLOCKED
- Transitions: VERIFY

## Testing Workflow Logic

### Manual API Testing
Using the HTTP client (`sample.http`), you can test:
1. SUBMIT event (DRAFT → PENDING_APPROVAL)
2. APPROVE event (PENDING_APPROVAL → ACTIVE) with manager approval guard
3. SUSPEND event (ACTIVE → SUSPENDED)
4. VERIFY event for supplier workflow

### Integration Testing
The `StatemachineApplicationTests.java` provides a basic test framework that can be expanded to test:
- Complete state transition flows
- Guard condition evaluation
- Action execution verification
- Error conditions and exceptions

## Key Considerations

1. **State Transitions are Atomic**: Each event firing either completes fully or fails entirely
2. **Workflow Versioning**: Workflows are identified by name + version (currently hardcoded to version 1)
3. **Database Persistence**: Workflow state is stored in database between events
4. **Extensibility**: New workflows, actions, and guards can be added without changing core code
5. **YAML-Driven Configuration**: All workflow logic changes go through YAML configuration files
6. **Git Commit Restrictions**: This repository has git commit restrictions enforced via .claude/settings.json to prevent accidental or unauthorized commits. If you need to make a permanent change, contact the repository administrator for temporary restriction lifting.

## Getting Started

1. Build with `mvn clean install`
2. Run the application with `mvn spring-boot:run` (defaults to port 8080)
3. Test using provided sample HTTP requests in `sample.http`
4. Explore by adding new workflows or modifying existing ones
5. Use JUnit tests to verify workflow behavior changes

## Project Notes

- Uses Spring Boot 4.1.0 with Java 25 (as specified in pom.xml)
- Supports both H2 (in-memory) and MariaDB for persistence
- Actions and guards are implemented as Spring components for dependency injection
- Testing includes basic `contextLoads()` test that can be expanded
- No existing CLAUDE.md or cursor rules present in the project