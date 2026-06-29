# ValueMeters Banking API — Specmatic Contract Testing

A full-stack online banking system with Specmatic contract testing integration.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 2.7.18 |
| Database | MySQL 8.0 |
| Security | JWT Authentication |
| API Docs | SpringDoc OpenAPI 3.0 |
| Contract Testing | Specmatic 2.47.0 |
| CI/CD | GitHub Actions |

---

## Prerequisites

- Java 17+
- MySQL 8.0
- Maven 3.8+

---

## Database Setup

```sql
CREATE DATABASE banking_db;
```

---

## Running the Application

```bash
# Default profile (JWT security enabled)
./mvnw spring-boot:run

# Test profile (security disabled for contract testing)
./mvnw spring-boot:run -Dspring-boot.run.profiles=test
```

**Application URLs:**

| URL | Description |
|---|---|
| http://localhost:9000 | API Base URL |
| http://localhost:9000/api-docs | OpenAPI Docs |
| http://localhost:9000/swagger-ui.html | Swagger UI |

---

## Running Specmatic Contract Tests

### Option 1 — Command Line

```bash
# Start application with test profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=test

# Run Specmatic contract tests
java -jar specmatic.jar test
```

### Option 2 — JUnit (Recommended)

```bash
./mvnw test -Dtest=BankingContractTest
```

---

## API Endpoints Covered

| Endpoint | Method | Description |
|---|---|---|
| /auth/register | POST | Register new user |
| /auth/login | POST | Login and get JWT token |
| /transaction/withdraw/{accountId} | POST | Withdraw money |
| /expense/summary/{accountId} | GET | Get expense summary |

---

## Contract Testing Overview

This project uses Specmatic to validate API contracts defined in OpenAPI specifications.

**Features:**
- OpenAPI-driven contract testing
- Automated provider verification
- Positive + negative resiliency test execution
- Externalized examples via `openapi_examples/` directory
- GitHub Actions CI integration
- HTML coverage report generation
- Contract-first API validation

### Test Results

```
Tests run: 33, Successes: 33, Failures: 0, WIP: 0, Errors: 0
100% API Coverage across all endpoints
```

### Latest Status

| Check | Status |
|---|---|
| Contract tests | ✅ 33/33 passing |
| API Coverage | ✅ 100% |
| GitHub Actions CI | ✅ Passing |
| HTML Report | ✅ Auto-generated |
| Resiliency Tests | ✅ Positive + Negative verified |

---

## Key Fix — Specmatic Resiliency Tests

During provider testing, Specmatic's positive resiliency execution was generating
random email values instead of using seeded test data, causing login and register
tests to fail.

**Root Cause:**

`"format": "email"` in the OpenAPI schema caused Specmatic to generate random
valid emails (e.g. `ahgtg@vismx.com`) during resiliency runs, which were not
present in the seeded H2 test database.

**Fix (few lines only):**

1. Removed `"format": "email"` from `LoginRequest` and `RegisterRequest` schemas in `openapi.json`
2. Updated register success example email to `newuser@example.com` (different from seeded `test@example.com`)
3. Added `openapi_examples/auth_register_success.json` to pin the register positive test to seeded data

**Before:**
```json
"email": {
  "type": "string",
  "format": "email",
  "example": "test@example.com"
}
```

**After:**
```json
"email": {
  "type": "string",
  "example": "test@example.com"
}
```

> Specmatic uses `format: email` to generate structurally valid but random emails
> during resiliency tests. Removing the format constraint pins generation to the
> example value, which matches seeded test data.

---

## Externalized Examples Structure

```
openapi_examples/
├── auth_login_success.json
├── auth_login_400.json
├── auth_register_success.json
├── auth_register_400.json
├── transaction_withdraw_success.json
├── transaction_withdraw_400.json
└── expense_summary_400.json
```

---

## CI/CD Integration

Contract tests run automatically on every push via GitHub Actions.

**GitHub Actions Workflow:**
https://github.com/KRameshr/valuemeters-specmatic/actions

---

## Live HTML Report

https://krameshr.github.io/valuemeters-specmatic/docs/specmatic-report/index.html

---

## Mocking with Specmatic

Specmatic can be used as a mock server during frontend development.

```bash
java -jar specmatic.jar stub
```

This starts a mock server that returns responses directly from the OpenAPI contract
without requiring the backend service — useful for frontend teams to develop
independently.

---

## Project Highlights

- Contract-First Development
- OpenAPI 3.0 Specification
- Automated Contract Testing with Specmatic
- Spring Boot REST APIs with JWT Authentication
- Positive and Negative Resiliency Test Coverage
- Externalized Examples for Predictable Test Data
- GitHub Actions Automation
- HTML Coverage Reporting
- Mock Server Support

---

## Blog Post

https://dev.to/krameshr/how-i-integrated-specmatic-contract-testing-into-a-real-banking-api-valuemeters-cak
