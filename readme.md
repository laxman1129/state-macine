# State-Machine

This project implements a configurable state-machine system where workflow definitions are managed through YAML configuration files and states are persisted to a database. The system supports REST API endpoints for firing workflow events with extensible action and guard mechanisms.

## Quick Start

```bash
# Build the project
mvn clean install

# Run the application (default port: 8080m)
mvn spring-boot:run

# Test using HTTP client (import sample.http into your IDE)
```

## API Usage

The system exposes a single REST endpoint for triggering workflow events:

**POST `/workflow/event`
- Firing a workflow event transitions an entity through its state machine
- Requires: workflowName, entityId, event, performedBy, optional data

### Sample Requests (see sample.http)

```json
// Submit a new station workflow
{
    "workflowName": "station",
    "entityId": 99,
    "event": "SUBMIT",
    "performedBy": "user123", 
    "data": {"itemName": "Sample Item"}
}
```

```json
// Approve a station workflow (requires managerApprovalGuard)
{
    "workflowName": "station", 
    "entityId": 99,
    "event": "APPROVE",
    "performedBy": "MANAGER",
    "data": {"itemName": "Sample Item", "role": "MANAGER"}
}
```

```json
// Verify a supplier workflow
{
    "workflowName": "supplier",
    "entityId": 55, 
    "event": "VERIFY",
    "performedBy": "user123",
    "data": {"supplierName": "ACME Supplies"}
}
```

## Workflows

### Station Workflow
**States:** DRAFT → PENDING_APPROVAL → ACTIVE → SUSPENDED   
**Transitions:** SUBMIT, APPROVE (requires managerApprovalGuard + sendEmailAction), SUSPEND

### Supplier Workflow   
**States:** CREATED → VERIFIED → BLOCKED
**Transitions:** VERIFY

## Development

- Actions: SendEmailAction, PublishEventAction  
- Guards: ManagerApprovalGuard (checks "role" field equals "MANAGER")
- All components follow Spring Boot conventions with dependency injection

## Testing

```bash
# Run all tests
mvn test

# Run integration tests specifically   
mvn spring-boot:test-runner
```