# ValueMeters Banking API — Specmatic Contract Testing

A full-stack online banking system with Specmatic contract testing integration.

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 2.7.18 |
| Database | MySQL 8.0 |
| Security | JWT Authentication |
| API Docs | SpringDoc OpenAPI 3.0 |
| Contract Testing | Specmatic 2.48.0 |
| CI/CD | GitHub Actions |

## Prerequisites

- Java 17+
- MySQL 8.0
- Maven 3.8+

## Database Setup

```sql
CREATE DATABASE banking_db;
```

## Running the Application

```bash
# Default profile (JWT security enabled)
./mvnw spring-boot:run

# Test profile (security disabled for contract testing)
./mvnw spring-boot:run -Dspring-boot.run.profiles=test
```

Application URLs:

| URL | Description |
|---|---|
| http://localhost:9000 | API Base URL |
| http://localhost:9000/api-docs | OpenAPI Docs |
| http://localhost:9000/swagger-ui.html | Swagger UI |

## Running Specmatic Contract Tests

```bash
./mvnw test -Dtest=BankingContractTest
```

## API Endpoints Covered

| Endpoint | Method | Description |
|---|---|---|
| /auth/register | POST | Register new user |
| /auth/login | POST | Login and get JWT token |
| /account/user/{userId} | GET | Get account by user ID |
| /account/{accountNumber} | GET | Get account by account number |
| /transaction/deposit/{accountId} | POST | Deposit money |
| /transaction/withdraw/{accountId} | POST | Withdraw money |
| /transaction/transfer/{fromAccountId} | POST | Transfer money |
| /transaction/history/{accountId} | GET | Get transaction history |
| /expense/add/{accountId} | POST | Add expense |
| /expense/list/{accountId} | GET | Get all expenses |
| /expense/summary/{accountId} | GET | Get expense summary |
| /budget/set/{accountId} | POST | Set budget limits |
| /budget/get/{accountId} | GET | Get budget limits |

## Contract Testing Overview

This project uses Specmatic to validate API contracts defined in OpenAPI specifications.

**Features:**
- OpenAPI-driven contract testing
- Automated provider verification
- Positive + negative resiliency test execution
- Externalized examples via `examples/` directory
- GitHub Actions CI integration
- HTML coverage report generation
- Contract-first API validation
- Actuator-based endpoint discovery

### Test Results

```
Tests run: 96, Successes: 96, Failures: 0, WIP: 0, Errors: 0
100% API Coverage across all 13 endpoints
```

### Latest Status

| Check | Status |
|---|---|
| Contract tests | ✅ 96/96 passing |
| API Coverage | ✅ 100% |
| GitHub Actions CI | ✅ Passing |
| HTML Report | ✅ Auto-generated |
| Resiliency Tests | ✅ Positive + Negative verified |
| Governance | ✅ 0 missed operations |

## Key Learning — Specmatic Resiliency Tests

During provider testing, Specmatic's positive resiliency execution was generating
random email values instead of using seeded test data, causing login and register
tests to fail.

**Root Cause:** `"format": "email"` in the OpenAPI schema caused Specmatic to
generate random valid emails during resiliency runs, which were not present in
the seeded H2 test database.

**Fix:**
1. Removed `"format": "email"` from `LoginRequest` and `RegisterRequest` schemas
2. Added externalized example files in `examples/` directory
3. Upgraded to Specmatic 2.48.0 with actuator-based coverage tracking
4. Added governance rules to enforce 100% coverage

## Examples Structure

```
examples/
├── auth_login_success.json
├── auth_login_400.json
├── auth_register_success.json
├── auth_register_400.json
├── account_user_success.json
├── account_user_404.json
├── account_by_number_success.json
├── account_by_number_404.json
├── transaction_deposit_success.json
├── transaction_deposit_400.json
├── transaction_deposit_404.json
├── transaction_withdraw_success.json
├── transaction_withdraw_400.json
├── transaction_withdraw_404.json
├── transaction_transfer_success.json
├── transaction_transfer_400.json
├── transaction_history_success.json  (auto-covered)
├── expense_add_success.json
├── expense_add_400.json
├── expense_add_404.json
├── expense_list_success.json
├── expense_summary_success.json
├── expense_summary_400.json
├── budget_set_success.json
├── budget_set_400.json
├── budget_get_success.json
└── budget_get_404.json
```

## CI/CD Integration

Contract tests run automatically on every push via GitHub Actions.

**GitHub Actions Workflow:**
https://github.com/KRameshr/valuemeters-specmatic/actions

## Live HTML Report

https://krameshr.github.io/valuemeters-specmatic/docs/specmatic-report/index.html

## Mocking with Specmatic

Specmatic can be used as a mock server during frontend development.

```bash
java -jar specmatic.jar stub
```

This starts a mock server that returns responses directly from the OpenAPI contract
without requiring the backend service.

## Project Highlights

- Contract-First Development with OpenAPI 3.0
- Specmatic 2.48.0 with Actuator Integration
- 13 Endpoints with Full Contract Coverage
- Positive and Negative Resiliency Test Coverage
- Externalized Examples for Predictable Test Data
- Governance Rules — 0 missed operations, 100% coverage enforced
- GitHub Actions Automation
- HTML Coverage Reporting
- Mock Server Support

## Blog Post

https://dev.to/krameshr/how-i-integrated-specmatic-contract-testing-into-a-real-banking-api-valuemeters-cak
