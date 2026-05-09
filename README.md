# Trading Diary

A lightweight Spring Boot application to record, manage, and view trading plans and executed trades (a simple trading diary).

[![Build Status](https://img.shields.io/badge/build-unknown-lightgrey.svg)](.) [![Java](https://img.shields.io/badge/java-21-blue.svg)](.)

## Table of contents

- [What the project does](#what-the-project-does)
- [Why it is useful](#why-it-is-useful)
- [Quick start](#quick-start)
  - [Requirements](#requirements)
  - [Build and run (locally)](#build-and-run-locally)
  - [Run from IDE](#run-from-ide)
- [Using the application (CLI menu)](#using-the-application-cli-menu)
- [Configuration](#configuration)
- [Project structure (tracked files)](#project-structure-tracked-files)
- [Where to get help](#where-to-get-help)
- [Maintainers & contributing](#maintainers--contributing)

## What the project does

Trading Diary is a console-driven Spring Boot application that lets you:

- Plan trades (planned trades) and confirm them.
- Log executed trades and close existing trades.
- View open trades and all trades with pagination.
- Persist data using an in-memory H2 database (configurable).

The application is packaged as a WAR and uses Spring Boot for wiring, JPA for persistence, and a simple menu-driven CLI for interaction.

## Why it is useful

- Simple way to track trading intentions and outcomes.
- Small codebase that demonstrates Spring Boot + JPA patterns.
- Useful for learning or as a starting point for more feature-rich trading diary apps.

## Quick start

### Requirements

- Java 21 (project property: `java.version=21` in `pom.xml`).
- Maven (the repo ships with the Maven wrapper `mvnw`).
- macOS/Linux zsh or Windows (adjust commands accordingly).

### Build and run (locally)

From the project root:

```bash
# using the included Maven wrapper (Unix/macOS - zsh)
./mvnw clean package

# run (Spring Boot)
./mvnw spring-boot:run

# or run the built WAR with an embedded container (if packaged as an executable WAR/JAR):
java -jar target/diary-0.0.1-SNAPSHOT.war
```

Notes:
- The project packaging is `war` (see `pom.xml`). The embedded Tomcat starter is present with `provided` scope for the server, so you can run with the Spring Boot plugin or deploy the WAR to an application server.

### Run from IDE

- Import the Maven project into your IDE (IntelliJ IDEA, Eclipse).
- Ensure the project SDK is set to Java 21.
- Run `com.trading.diary.TradingDiaryApplication` as a Java application.

## Using the application (CLI menu)

The application uses a menu-based CLI. When running, you'll be presented with the `Trading Diary Menu` and options such as:

- 1. Plan a Trade — create a planned trade
- 2. Log a Trade — directly add an executed trade
- 3. Open a planned trade — confirm a planned trade
- 4. Close a trade — close an active trade with closing price, targets, stoplosses
- 5. Update trade
- 6. View Open Trades — paginated
- 7. View All Trades — paginated
- 0. Exit

The menu implementation lives at: `src/main/java/com/trading/diary/menu/ApplicationMenu.java`.

## Configuration

Default configuration is in `src/main/resources/application.properties`.

Key properties:

- `spring.datasource.url=jdbc:h2:mem:tradingdiary` — in-memory H2 DB by default
- `spring.h2.console.enabled=true` and `spring.h2.console.path=/tradingh2` — access H2 console at `/tradingh2` when the app is running

Change datasource settings if you want a persistent DB (e.g., file-based H2 or another RDBMS).

## Project structure (tracked files)

Below is a focused list of the main files and directories (relative to repo root) along with short descriptions. Use these links to jump to implementation points mentioned above.

Top-level

- `pom.xml` — Maven project descriptor (Java 21, Spring Boot parent, dependencies)
- `mvnw`, `mvnw.cmd` — Maven wrapper
- `README.md` — this file

Source (main)

- `src/main/java/com/trading/diary/TradingDiaryApplication.java` — Spring Boot entry point

Packages (high level)

- `src/main/java/com/trading/diary/menu/` — menu-driven CLI
  - `ApplicationMenu.java` — primary menu and option routing
  - `AbstractMenu.java`, `Menu.java`, `MenuName.java` — menu abstractions
  - `factories/`, `helperMenus/`, `tradeMenus/`, `formation_menu/`, `paginationMenus/` — supporting menu implementations and factories

- `src/main/java/com/trading/diary/pojo/` — JPA entities and DTOs
  - `Company.java` — company entity
  - `Person.java` — person entity
  - `dto/` — request/response DTOs (e.g., `CloseTradeDTO`, `PlannedTradeConfirmationDTO`)

- `src/main/java/com/trading/diary/services/` — service layer
  - `TradeService.java` — core trade operations (add/close/count/list)
  - `PlannedTradeService.java`, `CompanyService.java`, `PersonService.java` — supporting services

- `src/main/java/com/trading/diary/repositories/` — Spring Data JPA repositories
  - `repositories/trade/TradeRepository.java` — JPA repo for trade entity
  - `repositories/trade/PlannedTradeRepository.java`

- `src/main/java/com/trading/diary/` (other packages)
  - `configs/` — app-specific configuration (e.g., `ApplicationShutdownManager`)
  - `formation/`, `explainers/`, `helpers/`, `scale/`, `trade/`, `utils/` — domain and utility classes

Resources

- `src/main/resources/application.properties` — runtime configuration
- `src/main/resources/static/` — static assets (if any)
- `src/main/resources/templates/` — view templates (if used)

Tests

- `src/test/java/` — unit/integration tests

Build output (local)

- `target/` — Maven build output (WAR, classes, libs)

If you prefer a complete tree dump, run locally:

```bash
# prints a concise tree of the repository
find . -maxdepth 3 -type d -print -o -type f -print
```

## Where to get help

- Project source files contain inline comments and package-level organization for guidance.
- Open an issue or discussion in this repository (if hosted on GitHub).
- For Spring Boot-specific questions, consult the official docs: https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/

## Maintainers & contributing

- Maintainer: repository owner
- To contribute:
  - Fork the repo, create a feature branch, and submit a PR.
  - Follow standard GitHub contribution practices. Add tests for new behavior where appropriate.
  - For larger changes, open an issue to discuss design first.

## Notes & next steps

- The project currently uses an in-memory H2 database. If you plan to use it beyond development, configure a persistent datasource in `application.properties`.
- Consider adding CI/CD workflow badges (build/test) if you add GitHub Actions or another CI service.

---

(README generated automatically — file list is indicative and focuses on the most relevant files for contributors.)

