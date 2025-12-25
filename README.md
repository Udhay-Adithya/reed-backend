# Reed Backend

Ktor-based backend service for the Reed music app.

## Features

- REST API built with **Ktor**
- **JWT** authentication
- SQL access via **Exposed**
- OpenAPI spec in `src/main/resources/openapi/documentation.yaml`

## Requirements

- JDK 17+

## Quickstart

### Run tests

```bash
./gradlew test
```

### Run locally

```bash
./gradlew run
```

Server defaults to `http://localhost:8080`.

## API documentation

- OpenAPI: `src/main/resources/openapi/documentation.yaml`
- Swagger UI: served by the app when enabled in routing/plugins.

## Configuration

- Ktor config: `src/main/resources/application.yaml`
- Don’t commit real secrets. Use local-only config for tokens/DB credentials.

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
