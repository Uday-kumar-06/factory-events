# Factory Events Backend

A Spring Boot backend service that ingests and stores machine-generated events from a factory environment.  
The system is designed to handle high-volume event ingestion reliably with a clean layered architecture.

---

## 🚀 Features

- REST API for ingesting machine events
- Batch ingestion support
- PostgreSQL persistence using Spring Data JPA
- Transactional ingestion logic
- Database-level idempotency guarantees
- Docker-based database setup
- Integration testing with Testcontainers

---

## 🏗️ Architecture Overview

The application follows a standard layered architecture:

- **Controller Layer**  
  Handles HTTP requests and responses

- **Service Layer**  
  Contains business logic and transactional boundaries

- **Repository Layer**  
  Manages persistence using Spring Data JPA

- **Domain Layer**  
  JPA entities representing factory events

---

## 🛠️ Tech Stack

- Java 22
- Spring Boot 4.x
- Spring Data JPA
- PostgreSQL
- Hibernate
- Maven
- Docker
- Testcontainers (for integration testing)

---

## 🗄️ Database Design

### Tables

- **machine_event**
  - `id` (primary key)
  - `machine_id`
  - `event_type`
  - `event_timestamp`
  - `created_at`

Database constraints are used to ensure data integrity and prevent duplicates.

---

## 🐳 Docker Setup (PostgreSQL)

The database can be started using Docker Compose.

### `docker-compose.yml`

```yaml
version: "3.8"

services:
  postgres:
    image: postgres:15
    container_name: factory-postgres
    restart: unless-stopped
    environment:
      POSTGRES_DB: factory_db
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
      TZ: UTC
      PGTZ: UTC
    ports:
      - "5433:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

volumes:
  postgres_data:
