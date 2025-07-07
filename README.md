# Transporter Assignment on Lanes

Spring Boot application that assigns transporters to shipment lanes while minimizing total cost and respecting transporter usage constraints.

---

## Tech Stack
- Java 21
- Spring Boot 3.5.3
- Maven
- JUnit 5
- Lombok

---

## Architecture Overview

### Monolithic, Modular Design
- No microservices – single domain-focused app
- Modular internals: `api`, `dto`, `service`, `solver`, `model`, `util`, `exception`

---

## Core Features

### ✅ Input Data
- `POST /api/v1/transporters/input`
- Accepts lane list and transporter quotes
- NOTE: Current input persistance is limited to an in-memory store with only single session persistance, in light of the limited scope
  

### ✅ Optimized Assignment
- `POST /api/v1/transporters/assignment`
- Assigns transporters to all lanes using:
  - ≤ N transporters (user-defined)
  - Minimum total quote cost
  - Full lane coverage

---

## ✨ Extra

### Strategy-Based Solver Interface
- Pluggable `SolverStrategy` design:
  - `GreedyCheapestFirstSolver`: fast, simple baseline
  - `BoundedSearchWithPruningSolver`: optimal selection using brute-force subset enumeration + cost pruning
- Spring profile-driven injection: swap solvers without changing logic


### Custom Validation Layer

## Error Handling

The application uses a **global exception handler** to provide clean, consistent error responses for all incoming API requests. This ensures a better developer experience and helps clients debug issues quickly.

### What’s Handled:

* **Invalid/Missing Fields** → `400 Bad Request`

  * Triggered when DTO validation fails (e.g., missing required fields, out-of-range values)
  * Returns a map of field-specific errors for easy debugging
* **Malformed JSON** → `400 Bad Request`

  * Triggered when the request body contains a badly formatted JSON payload
  * Provides a clear hint that syntax is incorrect
* **Empty Lanes/Transporters** → `400 Bad Request`
  * Lanes/transporters cannot be empty and will result in 400s

* **Unhandled Server Errors** → `500 Internal Server Error`

  * Any uncaught exception is caught and wrapped in a generic error response
  * Prevents leaking stack traces or app internals

---

### 🧪 Example Error Responses

**Validation Failure**

```json
{
  "message": "Invalid input",
  "status": "failed",
  "data": {
    "lanes": "must not be empty"
  }
}
```

**Malformed JSON / Invalid Enum**

```json
{
  "message": "error",
  "status": "Invalid input: malformed JSON.",
  "data": null
}
```

**Unhandled Exception**

```json
{
  "message": "error",
  "status": "Unexpected error occurred",
  "data": null
}
```
---

## Data Models

### Lane
```json
{ "id": 1, "origin": "Mumbai", "destination": "Delhi" }
```

### Transporter
```json
{
  "id": 1,
  "name": "T1",
  "laneQuotes": [ { "laneId": 1, "quote": 5000 }, ... ]
}
```

### Assignment Output
```json
{
  "totalCost": 29000,
  "assignments": [ { "laneId": 1, "transporterId": 1 }, ... ],
  "selectedTransporters": [1, 2, 3]
}
```

---

## Flow Summary
```
Client → Controller → Service → SolverStrategy (Greedy | Optimal) → PlanStatsBuilder → Response
```

---

## Package Layout
```
com.assignment
├── api            // REST controllers
├── dto            // Request/response payloads
├── model          // Lane, Transporter, LaneQuote
├── service
│   ├── core       // Input Services, Solvers
│   └── store      // In-memory store of lanes & transporters
├── util           // Mappers, etc
├── config         // Bean configs, solver profile wiring
└── exception      // Validation errors, 400 handlers
```

---

##  Build & Run
### Build
```bash
mvn clean install
```

### Run
```bash
mvn spring-boot:run
```

App runs on: `http://localhost:8080`

---

## Testing
### Run Tests
```bash
mvn test
```

---

## API Spec
See [OpenAPI JSON file](./transporter-lane-openapi.json) for full schema.

Import into:
- Swagger UI
- Postman
- Stoplight Studio

---


#### Thank you for your time.
——<br>
With sincere regard,<br>
Sairamkumar M<br>
[GitHub](https://github.com/sairamkumarm) | [LinkedIn](https://www.linkedin.com/in/sairamkumarm/)

---

