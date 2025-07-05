# Transporter Assignment on Lanes

Spring Boot application that assigns transporters to shipment lanes while minimizing total cost and respecting transporter usage constraints.

---

## Tech Stack
- Java 17
- Spring Boot 3.x
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

### ✅ Optimized Assignment
- `POST /api/v1/transporters/assignment`
- Assigns transporters to all lanes using:
  - ≤ N transporters (user-defined)
  - Minimum total quote cost
  - Full lane coverage

---

## ✨ Standout Features (Beyond Spec)

### Strategy-Based Solver Interface
- Pluggable `SolverStrategy` design:
  - `GreedyCheapestFirstSolver`: fast, simple baseline
  - `BoundedSearchWithPruningSolver`: optimal selection using brute-force subset enumeration + cost pruning
- Spring profile-driven injection: swap solvers without changing logic

### Plan Metrics in Response
```json
"planStats": {
  "lanesCovered": 10,
  "transportersUsed": 3,
  "coverageRatio": 1.0,
  "avgQuotePerLane": 2900,
  "cheapestLaneMissedByGreedy": 2
}
```
Helps analyze correctness and efficiency beyond surface cost.

### CSV Export Endpoint
- `GET /api/v1/transporters/export?format=csv`
- Returns: `laneId,origin,destination,transporterId,quote`
- Usable by ops and analysts directly in Excel

### Custom Validation Layer
- Validates non-empty quotes, transporter coverage
- Graceful 400 responses with error field details

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
  "selectedTransporters": [1, 2, 3],
  "planStats": { ... }
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
│   └── store      // In-memory store of lanes & transporters
├── solver         // Greedy + Optimal strategies
├── util           // PlanStatsBuilder, CSVBuilder
├── config         // Bean configs, solver profile wiring
├── exception      // Validation errors, 400 handlers
└── test           // Unit + integration tests
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

Coverage includes:
- Happy path for each endpoint
- Greedy vs optimal output comparison
- Edge cases: empty lanes, duplicate IDs, over-capacity

---

## API Spec
See [OpenAPI JSON file](./transporter-lane-openapi.json) for full schema.

Import into:
- Swagger UI
- Postman
- Stoplight Studio

---

## Solver Comparison
| Strategy                  | Optimal? | Time Complexity      |
|---------------------------|----------|-----------------------|
| `GreedyCheapestFirst`     | ❌        | O(L * T)              |
| `BoundedSearchWithPruning`| ✅        | O(2^T * L) (small N only) |

Use greedy for quick demos, optimal for correctness or benchmarks.

---

## Author
Sairam – Built for FreightFox

---

