# Contributing

Thanks for being willing to contribute to **Reed Backend**.

## Tech stack

- Kotlin + Gradle
- Ktor (server)
- Exposed (SQL)
- JWT auth
- OpenAPI/Swagger docs (see `src/main/resources/openapi/documentation.yaml`)

## Prerequisites

- **JDK 17+** (recommended: Temurin 17)
- **Gradle** (optional; the project includes the wrapper `./gradlew`)

## Project layout

- `src/main/kotlin/com/reed` – application code
- `src/main/resources/application.yaml` – Ktor configuration
- `src/main/resources/openapi/documentation.yaml` – API spec
- `src/test/kotlin` – tests

## Setup (local)

1. **Clone** the repo.
2. **Configure environment** (if needed).

   The repo includes `local.properties` / `application.yaml` for configuration. Avoid committing secrets.

3. **Build and test**:

   ```bash
   ./gradlew test
   ```

4. **Run the server**:

   ```bash
   ./gradlew run
   ```

   By default it should start on `http://localhost:8080`.

## API docs

- OpenAPI spec: `src/main/resources/openapi/documentation.yaml`
- Swagger UI is enabled in the app configuration (see routing/plugins).

## Code style

- Keep changes focused and small.
- Prefer explicit names and clear error handling.
- Add/update tests for any behavior change.

## Making changes

1. Create a branch: `git checkout -b yourname/short-topic`
2. Make changes.
3. Run quality gates locally:

   ```bash
   ./gradlew test
   ./gradlew build
   ```

4. Open a PR.

## Commit / PR guidelines

- Use descriptive PR titles.
- Include:
  - what changed
  - why it changed
  - how to test
  - any API impact

## Reporting issues

Please include:

- steps to reproduce
- expected vs actual behavior
- logs (redact secrets)
- OS + JDK version

