# MOD Recruitment System – Backend

## Overview
This backend powers the **MOD Recruitment System**. It provides APIs for:
- authentication
- applicant submission with CV upload
- application status tracking
- HR review workflow
- dashboard statistics
- Super Admin user management
- simulated NID and NESA lookup services

## Repository
Backend repository: `https://github.com/mugishavictor/mod-recruitment-system-backend`

## Tech Stack
- Java 17
- Spring Boot 3
- Spring Security
- JWT Authentication
- Spring Data JPA
- PostgreSQL
- Flyway
- Maven
- Swagger / OpenAPI

## Core Business Roles
- `SUPER_ADMIN`
- `HR`
- `APPLICANT`

## Core Features
### Authentication
- login endpoint for system users
- JWT generation after successful login

### Applicant features
- submit full application profile
- upload CV
- receive application reference code
- track application status using reference code

### HR features
- view latest 10 applications
- list returned alphabetically after selecting latest records
- view application details
- approve or reject with reason

### Super Admin features
- create users
- update users
- activate/deactivate users

### Dashboard
- total applications
- approved applications
- rejected applications
- pending applications

### Simulation APIs
- NID simulation
- NESA simulation

## Prerequisites
Install these before running the backend:
- Java 17
- Maven or the included Maven Wrapper
- PostgreSQL

## Project Structure
```text
backend/
├── pom.xml
├── src/main/java/com/example/recruitment/
│   ├── config/
│   ├── common/
│   ├── auth/
│   ├── user/
│   ├── application/
│   ├── integration/
│   └── dashboard/
└── src/main/resources/
    ├── application.yml
    └── db/migration/
```

## Database Setup
Create a PostgreSQL database, for example:

```sql
CREATE DATABASE recruitment_db;
```

## Configuration
Edit `src/main/resources/application.yml` or use environment variables.

Example local configuration:

```yaml
server:
  port: 8081

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/recruitment_db
    username: postgres
    password: postgres

  jpa:
    hibernate:
      ddl-auto: validate
    properties:
      hibernate:
        format_sql: true
    open-in-view: false

  servlet:
    multipart:
      max-file-size: 5MB
      max-request-size: 5MB

app:
  jwt:
    secret: your-long-secret-key
    expiration-ms: 86400000

  upload-dir: uploads

cors:
  allowed-origin: http://localhost:3000
```

## Environment Variables (Recommended for Deployment)
You can externalize configuration with env vars like:

```env
PORT=8081
DATABASE_URL=jdbc:postgresql://localhost:5432/recruitment_db
DB_USERNAME=postgres
DB_PASSWORD=postgres
JWT_SECRET=your-long-secret-key
JWT_EXPIRATION_MS=86400000
UPLOAD_DIR=uploads
FRONTEND_URL=http://localhost:3000
```

## Installation
```bash
git clone https://github.com/mugishavictor/mod-recruitment-system-backend.git
cd mod-recruitment-system-backend
```

## Run Locally
Using Maven wrapper:

```bash
./mvnw spring-boot:run
```

Or with Maven:

```bash
mvn spring-boot:run
```

The API will be available at:

```text
http://localhost:8081
```

## Build the Project
```bash
./mvnw clean package
```

Generated jar:

```text
target/recruitment-0.0.1-SNAPSHOT.jar
```

Run jar directly:

```bash
java -jar target/recruitment-0.0.1-SNAPSHOT.jar
```

## Database Migrations
Flyway migration files are in:

```text
src/main/resources/db/migration/
```

Typical migration files:
- `V1__init_schema.sql`
- `V2__seed_roles_users.sql`

These create tables and seed default system roles and users.

## Seed Data / Required Data to Enter the System
### Default roles
- `SUPER_ADMIN`
- `HR`
- `APPLICANT`

### Demo users
These must exist in the database seed:
- `admin@recruitment.com / Admin@123`
- `hr@recruitment.com / Hr@123`

### Applicant-submitted data
Applicants provide:
- NID
- first name
- last name
- phone
- email
- date of birth
- address
- grade
- school option
- CV file

## File Upload
CV upload rules:
- accepted file types: PDF, DOC, DOCX
- max file size: 5MB
- files are stored in the upload directory configured by `app.upload-dir`

## API Documentation
Swagger UI:

```text
http://localhost:8081/swagger-ui/index.html
```

OpenAPI JSON:

```text
http://localhost:8081/v3/api-docs
```

## Main API Endpoints
### Auth
```text
POST /api/v1/auth/login
```

### Simulations
```text
GET /api/v1/simulations/nid/{nid}
GET /api/v1/simulations/nesa/{nid}
```

### Applicant
```text
POST /api/v1/applications
GET /api/v1/applications/status/{referenceCode}
```

### HR
```text
GET /api/v1/hr/applications
GET /api/v1/hr/applications/{id}
PATCH /api/v1/hr/applications/{id}/review
```

### Dashboard
```text
GET /api/v1/dashboard/stats
```

### Super Admin
```text
GET /api/v1/users
POST /api/v1/users
PUT /api/v1/users/{id}
PATCH /api/v1/users/{id}/status
```

## Authentication Details
- Login returns a JWT token
- Protected endpoints require:

```text
Authorization: Bearer <token>
```

## Review Logic
To satisfy the challenge wording:
- the backend first selects the latest 10 applications by submission date
- then sorts those 10 alphabetically by applicant name before returning them

## Deployment Notes
When deploying, make sure:
- database connection values are set
- JWT secret is provided
- frontend origin is whitelisted in CORS
- upload directory exists or is writable
- Swagger is reachable for reviewer testing

## Common Problems
### Flyway migration error
If Flyway says a table does not exist even though migration history exists, use a fresh database or reset the schema.

### Swagger returns 500
Check `springdoc-openapi` version compatibility with Spring Boot.

### Login returns bad credentials
Verify:
- seeded users exist
- BCrypt passwords match the intended cleartext passwords

### Frontend cannot call backend
Check:
- CORS allowed origin
- backend port
- frontend API base URL

## Test Checklist
Before sharing the backend:
- login works
- status lookup works
- application submission works
- CV upload works
- HR review works
- dashboard stats load
- user management works
- Swagger UI opens successfully

## License
This project was developed as part of a coding challenge submission.