# Repository Guidelines

## Project Structure & Module Organization
- Root: Gradle wrapper (`gradlew`), shared config (`build.gradle`, `settings.gradle`).
- `mps-api/`: Spring Boot service (controllers, services, DTOs).
  - Source: `mps-api/src/main/java/com/mizegret/mps/mps_api/...`
  - Config: `mps-api/src/main/resources/application.yml`
  - Tests: `mps-api/src/test/java/...`
- `mps-core/`: Shared library (DTOs, handlers). Packs as a plain JAR (`bootJar` disabled).

## Build, Test, and Development Commands
- Build all: `./gradlew build` — compiles modules and runs tests.
- Run API: `./gradlew :mps-api:bootRun` — starts the HTTP service locally.
- Test all: `./gradlew test` — runs JUnit 5 tests across modules.
- Test one class: `./gradlew :mps-api:test --tests "*ScrapeControllerTest"`.
- Clean: `./gradlew clean` — removes build outputs.

## Coding Style & Naming Conventions
- Language: Java 21, Spring Boot 3.
- Indentation: 4 spaces; max line length ~120 where practical.
- Packages: `com.mizegret.mps.*` (e.g., `controller`, `service`, `dto`).
- Classes: PascalCase (e.g., `ScrapeServiceImpl`); methods/fields: camelCase.
- DTOs end with `Request`/`Response`; tests end with `Test`.
- Lombok is available; prefer `@Value`/`@Data` over boilerplate, but keep API models explicit.

## Testing Guidelines
- Framework: JUnit 5 (`useJUnitPlatform()`); Spring Boot test starter included.
- Scope: Unit tests for services; slice or MVC tests for controllers.
- Naming: Mirror package and class under `src/test/java`; method names describe behavior.
- Running locally: see commands above; add focused tests near changes.

## Commit & Pull Request Guidelines
- Commits: Use Conventional Commits where possible (`feat:`, `fix:`, `chore:`, `test:`). Keep messages imperative and scoped (e.g., `feat(api): add scrape endpoint`).
- PRs: Include summary, rationale, and linked issue. Note breaking changes and config impacts. Attach sample requests/responses for API changes.
- CI/readiness: Ensure `./gradlew build` passes and tests cover new logic.

## Security & Configuration Tips
- Do not commit secrets. Keep credentials out of `application.yml`; prefer environment variables or external config.
- Validate and sanitize inputs in controllers; centralize error handling in shared handlers.
