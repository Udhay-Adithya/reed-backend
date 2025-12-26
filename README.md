# Reed Backend

Ktor-based backend service for the Reed music app.

## Features

- REST API built with **Ktor**
- **JWT** authentication
- **PostgreSQL** database with **Exposed** ORM
- OpenAPI spec in `src/main/resources/openapi/documentation.yaml`

## Requirements

- JDK 17+
- PostgreSQL (or Docker to run PostgreSQL)

## Quickstart

### 1. Set up PostgreSQL

Run PostgreSQL using Docker (easiest):

```bash
docker run --name reed-postgres \
  -e POSTGRES_DB=reed \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  -d postgres:16-alpine
```

Or install PostgreSQL locally:
- Mac: `brew install postgresql@16` or use [Postgres.app](https://postgresapp.com/)
- See [POSTGRESQL_MIGRATION.md](./POSTGRESQL_MIGRATION.md) for more options

### 2. Configure environment

Copy or update `.env` file with your database credentials (defaults work with Docker command above).

### 3. Run tests

```bash
./gradlew test
```

### 4. Run locally

```bash
./gradlew run
```

Server defaults to `http://localhost:8080`. Database tables are created automatically.

## API documentation

- OpenAPI: `src/main/resources/openapi/documentation.yaml`
- Swagger UI: served by the app when enabled in routing/plugins.

## Configuration

- Ktor config: `src/main/resources/application.yaml`
- Database & JWT config: `.env` file (PostgreSQL connection, JWT secrets)
- Don't commit real secrets. Use `.env` for local credentials.

## Common Gradle tasks

| Task | Description |
| --- | --- |
| `./gradlew test` | Run unit tests |
| `./gradlew build` | Build the project |
| `./gradlew run` | Run the server |
| `./gradlew buildFatJar` | Build an executable (fat) JAR |
| `./gradlew buildImage` | Build a Docker image |
| `./gradlew runDocker` | Run using the local Docker image |

## Contributing

See [`CONTRIBUTING.md`](./CONTRIBUTING.md).
