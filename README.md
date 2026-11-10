# Construction Project Tracking System

A **Java Spring Boot REST API** for organizing construction projects, assigning work, monitoring deadlines, and tracking completion. Built as an **independent portfolio reimplementation inspired by experience contributing to a construction tracking application during a TwinLit internship**. This is **not** TwinLit's original source code or a reproduction of its proprietary implementation.

## Features

- Create, list, edit, and delete construction projects
- Store project start dates, deadlines, descriptions, and statuses
- Register employees and assign them to tasks
- Create, edit, list, and delete tasks under projects
- Track task priority and status (`TODO`, `IN_PROGRESS`, `BLOCKED`, `DONE`)
- Calculate project completion percentage from finished tasks
- Flag unfinished tasks with past due dates as overdue
- Validate request fields and return meaningful HTTP error codes
- Persist data using Spring Data JPA (H2 for quick start, PostgreSQL optional)
- JUnit 5 / Mockito unit tests

## Tech stack

Java 21 · Spring Boot 3.5 · Spring Web · Spring Data JPA · Hibernate · Jakarta Validation · Maven · H2 · PostgreSQL · JUnit 5 · Mockito

## Quick start

Requirements: **JDK 21** and **Maven 3.9+**.

```bash
mvn spring-boot:run
```

By default the API uses an **in-memory H2 database**. Data resets when the process stops.

For PostgreSQL, start the database and activate the profile:

```bash
docker compose up -d postgres
mvn spring-boot:run -Dspring-boot.run.profiles=postgres
```

The Docker Compose example uses local development credentials only; change them before any non-local deployment.

## Example API usage

Create a project:

```bash
curl -X POST http://localhost:8080/api/projects \
  -H 'Content-Type: application/json' \
  -d '{"name":"Apartment Renovation","description":"Interior renovation","status":"ACTIVE","startDate":"2026-11-01","deadline":"2027-02-01"}'
```

Create an employee:

```bash
curl -X POST http://localhost:8080/api/employees \
  -H 'Content-Type: application/json' \
  -d '{"name":"Alex Morgan","email":"alex@example.com","role":"Site Engineer"}'
```

Create a task (replace IDs with actual IDs):

```bash
curl -X POST http://localhost:8080/api/projects/1/tasks \
  -H 'Content-Type: application/json' \
  -d '{"title":"Inspect foundation","priority":"HIGH","status":"TODO","dueDate":"2026-12-01","assigneeId":1}'
```

View project progress:

```bash
curl http://localhost:8080/api/projects/1
```

Update task status:

```bash
curl -X PUT http://localhost:8080/api/projects/1/tasks/1 \
  -H 'Content-Type: application/json' \
  -d '{"title":"Inspect foundation","priority":"HIGH","status":"DONE","dueDate":"2026-12-01","assigneeId":1}'
```

## Endpoints

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/projects` | Create project |
| GET | `/api/projects` | List projects with progress |
| GET | `/api/projects/{id}` | View project and progress |
| PUT | `/api/projects/{id}` | Update project |
| DELETE | `/api/projects/{id}` | Delete project and tasks |
| POST | `/api/employees` | Register employee |
| GET | `/api/employees` | List employees |
| POST | `/api/projects/{id}/tasks` | Create task |
| GET | `/api/projects/{id}/tasks` | List tasks |
| PUT | `/api/projects/{id}/tasks/{taskId}` | Update task |
| DELETE | `/api/projects/{id}/tasks/{taskId}` | Delete task |

## Testing

```bash
mvn test
```

Unit tests cover invalid project dates, missing projects, empty progress, and overdue task logic. **The tests have not been executed in the packaging environment**; run `mvn test` locally to verify them.

## Design and limitations

This is a learning/portfolio REST API, not a production construction management platform. It currently has no authentication, role-based access control, pagination, audit log, or frontend. For production use, add Spring Security, database migrations (Flyway), integration tests, and deployment configuration.

## Project background

During my internship at **TwinLit**, I contributed to an internal Construction Project Tracking System by helping refine features, track task progress, debug behavior, and test application functionality. This repository is a new Java-based demonstration of similar general concepts, written for my portfolio; it does not contain employer code, private data, or internal design documents.
