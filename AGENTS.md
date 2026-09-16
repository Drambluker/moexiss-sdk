# Repository Guidelines

## Project Structure & Module Organization

This repository is a Java 21 Maven library for the MOEX ISS API. Production code lives under `src/main/java/org/vlaskin/moexiss`. Keep public client entry points near `MoexClient`, HTTP concerns in `http`, endpoint logic in `service`, request options in `params`, and response models in `entity`. JSON deserialization infrastructure belongs in `response`. Tests mirror these packages under `src/test/java`; reusable offline API snapshots are in `src/test/resources/responses`. Maven Site configuration is in `src/site`, static-analysis configuration in `config`, and CI workflows in `.github/workflows`.

## Build, Test, and Development Commands

- `./mvnw clean verify` compiles the library, runs unit tests, validates Javadocs, builds artifacts, and enforces JaCoCo thresholds.
- `./mvnw test` runs the fast JUnit test suite without the full verification lifecycle.
- `./mvnw clean verify -Plive-contract-tests` also runs `*IT` tests against `iss.moex.com`; use only when network access is available.
- `./mvnw spotbugs:check` runs the configured maximum-effort SpotBugs analysis.
- `./mvnw clean verify site` generates Javadocs, reports, and the Maven Site in `docs/`.

Use the checked-in Maven wrapper. The build enforces Java 21 and Maven 3.9.5 or newer.

## Coding Style & Naming Conventions

Follow the existing Java style: four-space indentation, opening braces on a new line, one public top-level type per file, and explicit imports. Use `PascalCase` for classes, `camelCase` for methods and fields, and descriptive suffixes such as `*Service`, `*Params`, `*Response`, and `*Test`. Keep endpoint construction in services and transport behavior behind `MoexHttpTransport`. Document public API changes with valid Javadoc; packaging fails on Javadoc warnings. Lombok is allowed where already established, subject to `lombok.config`.

## Testing Guidelines

Tests use JUnit Jupiter. Name unit tests `*Test` and live integration tests `*IT`. Prefer `FixtureTransport` and committed JSON fixtures over network calls. Add or update fixtures when MOEX response shapes change. `verify` requires at least 90% line coverage and 65% branch coverage.

## Commit & Pull Request Guidelines

Recent commits use an imperative subject prefixed by a bracketed area, for example `[test] Cover cursor parsing` or `[docs] Explain custom transports`. Keep commits focused. Pull requests should explain the behavior and API impact, link relevant issues, list verification commands, and update tests, fixtures, README, or Javadocs as applicable. Call out any live-contract result separately because external API availability can affect it. Never commit GitHub tokens or Maven credentials; keep package credentials in `~/.m2/settings.xml`.
