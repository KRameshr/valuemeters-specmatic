![Specmatic CI](https://github.com/KRameshr/valuemeters-specmatic/actions/workflows/specmatic.yml/badge.svg)
# ValueMeters Banking API — Specmatic Contract Testing

A full-stack online banking system with Specmatic contract testing integration. 

## Tech Stack
- Java 17, Spring Boot 2.7.18
- MySQL 8.0
- JWT Authentication
- springdoc-openapi (OpenAPI 3.0)
- Specmatic 2.47.0

## Prerequisites
- Java 17+
- MySQL 8.0 running locally
- Maven 3.8+

## Database Setup
```sql
CREATE DATABASE banking_db;
```

## Running the Application
```bash
# Default profile (with JWT security)
./mvnw spring-boot:run

# Test profile (security disabled for contract testing)
./mvnw spring-boot:run -Dspring-boot.run.profiles=test
```

App runs on: http://localhost:9000
API Docs: http://localhost:9000/api-docs
Swagger UI: http://localhost:9000/swagger-ui.html

## Running Specmatic Contract Tests

### Option 1 — Command Line
```bash
# Step 1: Start app with test profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=test

# Step 2: In a new terminal
java -jar specmatic.jar test
```

### Option 2 — JUnit
```bash
./mvnw test -Dtest=BankingContractTest
```

## Contract Test Results
- 29 scenarios tested across 4 API endpoints
- 26 passing, 3 failures
- 63% API coverage

Live HTML Report:
https://krameshr.github.io/valuemeters-specmatic/docs/specmatic-report/index.html

## API Endpoints Covered
| Endpoint | Method | Coverage |
|---|---|---|
| /auth/register | POST | 50% |
| /auth/login | POST | 50% |
| /transaction/withdraw/{accountId} | POST | 50% |
| /expense/summary/{accountId} | GET | 100% |

## Blog Post
https://dev.to/krameshr/how-i-integrated-specmatic-contract-testing-into-a-real-banking-api-valuemeters-cak
