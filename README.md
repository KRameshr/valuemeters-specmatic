# ValueMeters Banking API — Specmatic Contract Testing

A full-stack online banking system with Specmatic contract testing integration.

## Tech Stack

* Java 17
* Spring Boot 2.7.18
* MySQL 8.0
* JWT Authentication
* SpringDoc OpenAPI 3.0
* Specmatic 2.47.0
* GitHub Actions CI/CD

## Prerequisites

* Java 17+
* MySQL 8.0
* Maven 3.8+

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

* API Base URL: http://localhost:9000
* OpenAPI Docs: http://localhost:9000/api-docs
* Swagger UI: http://localhost:9000/swagger-ui.html

## Running Specmatic Contract Tests

### Option 1 — Command Line

```bash
# Start application with test profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=test

# Run Specmatic contract tests
java -jar specmatic.jar test
```

### Option 2 — JUnit

```bash
./mvnw test -Dtest=BankingContractTest
```

## Contract Testing Overview

This project uses Specmatic to validate API contracts defined in OpenAPI specifications.

Features:

* OpenAPI-driven contract testing
* Automated provider verification
* Contract test execution through JUnit
* GitHub Actions CI integration
* HTML coverage report generation
* Contract-first API validation

### Latest Status

* Contract tests executing successfully
* GitHub Actions CI pipeline passing
* HTML report generated automatically
* OpenAPI contract verification enabled

### Live HTML Report

https://krameshr.github.io/valuemeters-specmatic/docs/specmatic-report/index.html

## API Endpoints Covered

| Endpoint                          | Method |
| --------------------------------- | ------ |
| /auth/register                    | POST   |
| /auth/login                       | POST   |
| /transaction/withdraw/{accountId} | POST   |
| /expense/summary/{accountId}      | GET    |

## CI/CD Integration

Contract tests run automatically on every push using GitHub Actions.

GitHub Actions Workflow:

https://github.com/KRameshr/valuemeters-specmatic/actions

## Mocking with Specmatic

Specmatic can be used as a mock server during frontend development.

```bash
java -jar specmatic.jar stub
```

This starts a mock server that returns responses directly from the OpenAPI contract without requiring the backend service.

## Project Highlights

* Contract-First Development
* OpenAPI 3.0 Specification
* Automated Contract Testing
* Spring Boot REST APIs
* JWT Authentication
* GitHub Actions Automation
* HTML Coverage Reporting
* Mock Server Support

## Blog Post

https://dev.to/krameshr/how-i-integrated-specmatic-contract-testing-into-a-real-banking-api-valuemeters-cak
