# AGENTS.md

## Project

This repository is a SQL OJ judging system for students to practice SQL.

Codex must follow these documents:

- PROJECT_SPEC.md
- DOCS_MANIFEST.md
- docs/00-rules/
- docs/09-tasks/

## Tech Stack

Backend:

- Java 17
- Spring Boot 3
- Maven
- MyBatis-Plus
- MySQL 8
- Redis
- JUnit 5

Frontend:

- Vue 3
- Vite
- TypeScript
- Element Plus
- Monaco Editor
- Pinia
- Axios
- Vue Router

Deploy:

- Docker Compose
- Nginx
- MySQL
- Redis

## Architecture Rules

- Use frontend-backend separation.
- Backend is a modular monolith, not microservices.
- Backend must follow DDD style package structure:
  - interfaces
  - application
  - domain
  - infrastructure
  - common
- Domain layer must not depend on Spring, MyBatis, Controller, Request, Response, Entity, or Mapper.
- Controller must not call Mapper directly.
- ApplicationService must not call Mapper directly.
- Judge engine must be independent and must not be coupled with Controller or CRUD service.

## SQL Judge Rules

V1 only supports MySQL SELECT and WITH queries.

Forbidden SQL keywords include:

- INSERT
- UPDATE
- DELETE
- DROP
- ALTER
- TRUNCATE
- CREATE
- REPLACE
- GRANT
- REVOKE
- CALL
- EXEC
- LOAD_FILE
- INTO OUTFILE
- INTO DUMPFILE
- SLEEP
- BENCHMARK

Multiple statements are forbidden. A single trailing semicolon is allowed.

## Codex Working Rules

Codex must execute only one task file at a time.

Before making changes, Codex must:

1. Read the current task file under docs/09-tasks.
2. Read all required documents listed by the task file.
3. Analyze the current code.
4. Output an execution plan.
5. Modify only files within the allowed scope.
6. Run relevant tests or explain why tests cannot run.
7. Output changed files, validation result, risks, and unfinished items.

## Git Rules

One task, one branch, one commit.

Commit message types:

- docs
- feat
- fix
- refactor
- test
- chore

Examples:

- docs: initialize sql oj documentation system
- feat: create backend spring boot skeleton
- feat: add problem module
- feat: add sql judge engine
- test: add sql safety checker tests

## Do Not

- Do not rewrite the entire project in one task.
- Do not introduce new tech stack without confirmation.
- Do not delete working code without explanation.
- Do not modify files outside the task scope.
- Do not skip tests when tests are available.