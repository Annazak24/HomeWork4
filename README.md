# Homework #4: Selenoid & Citrus Integration

This project demonstrates a production-grade test automation setup using **Selenoid** for browser orchestration, **Citrus Framework** for integration testing, and **Chrome Mobile Emulation**.

---

## Prerequisites

Before running the project, ensure the following are installed:

- Docker & Docker Desktop
- Java 17+ (OpenJDK 25 was used during development)
- Maven

---

## Selenoid Configuration

Selenoid-related configuration is located in the project root:

- `selenoid/browsers.json` — browser configuration for Selenoid  
- `docker-compose.yml` — **playbook for Selenoid deployment**

---

## Selenoid Setup and Launch (Recommended)

Selenoid and Selenoid UI are deployed using **Docker Compose**.

From the project root, run:

```bash
docker-compose up -d
```

### Verify Selenoid is running

Open the following URLs in your browser:

- http://localhost:4444/status — Selenoid status
- http://localhost:8080 — Selenoid UI (VNC)

---

## Alternative: Manual Selenoid Startup (Optional)

If Docker Compose is not used, Selenoid can be started manually:

```bash
docker run -d   --name selenoid   -p 4444:4444   -v /var/run/docker.sock:/var/run/docker.sock   -v "${PWD}/selenoid:/etc/selenoid:ro"   aerokube/selenoid:latest-release
```

---

## Running the Tests

Tests can be executed via **IntelliJ IDEA** or **Maven**.

### Run tests using Maven in remote (Selenoid) mode

```bash
mvn test -Drun.type=remote -Dbrowser.name=chrome
```

---

## Project Implementation Details

### Selenoid Integration
WebDriver instances connect to Selenoid Hub at:

```
http://localhost:4444/wd/hub
```

### Citrus Framework
Tests are implemented using `CitrusExtension`, enabling advanced validation, reporting, and test orchestration.

### Mobile Testing
Chrome Mobile Emulation is configured using the **Nexus 5** device profile to satisfy mobile browser testing requirements.

### Stability Configuration
Selenoid session timeouts are configured to ensure stable execution during browser startup and test initialization.

---

## Performance Notes

A short delay during test startup is expected due to initialization of two dependency injection systems:

- **Citrus Context** — reporting, validation, and orchestration
- **Google Guice** — dependency injection for Page Objects and WebDriver instances

This architecture provides high modularity and extensibility while maintaining stable execution.

---

## Visualizing Test Execution

Once the tests start, open the following URL to watch execution via VNC:

```
http://localhost:8080
```

You will see the mobile browser emulation running inside **Selenoid UI**.
