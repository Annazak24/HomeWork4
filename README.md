Homework #4: Selenoid & Citrus Integration

This project demonstrates a production-grade test automation setup using Selenoid for browser orchestration, Citrus Framework for integration testing, and Chrome Mobile Emulation.

Prerequisites

Before running the tests, ensure you have the following installed:

Docker & Docker Desktop

Java 17+ (OpenJDK 25 was used during development)

Maven

1. Selenoid Configuration and Launch

The project includes a selenoid directory in the project root that contains the browsers.json configuration file.

Start Selenoid Hub

Open your terminal (PowerShell or Bash) in the project root and run:

docker run -d `
    --name selenoid `
    -p 4444:4444 `
    -v //var/run/docker.sock:/var/run/docker.sock `
    -v "${PWD}/selenoid:/etc/selenoid:ro" `
    aerokube/selenoid:latest-release

Start Selenoid UI (Optional)

To visualize test execution in real time via VNC, run:

docker run -d `
    --name selenoid-ui `
    -p 8080:8080 `
    --link selenoid `
    aerokube/selenoid-ui:latest-release --selenoid-uri http://selenoid:4444

2. Running the Tests

Tests can be executed either via IntelliJ IDEA or using Maven.

Run tests via Maven in Remote (Selenoid) mode:
mvn test -Drun.type=remote -Dbrowser.name=chrome

3. Project Implementation Details

Selenoid Hub
WebDriverFactory connects to the hub at:
http://localhost:4444/wd/hub

Citrus Framework
Tests are integrated using CitrusExtension, enabling advanced validation, reporting, and test orchestration.

Mobile Emulation
Chrome Mobile Emulation is configured using the Nexus 5 device profile to satisfy mobile testing requirements.

Stability
A sessionTimeout of 15 minutes is configured to ensure the Selenoid session remains active during the initialization phase.

4. Performance Note

You may notice a short delay during test startup.
This is an expected technical trade-off caused by initializing two Dependency Injection systems:

Citrus Context
Initializes reporting, validation, and coordination components.

Google Guice
Manages dependency injection for Page Objects and WebDriver instances.

This architecture provides high modularity, extensibility, and powerful validation, at the cost of slightly increased startup time.
Timeouts and configurations have been tuned to ensure stable execution.

5. Visualizing Tests

Once the tests start, open the following URL in your browser to watch execution via VNC:

http://localhost:8080


You will see the mobile browser emulation running inside Selenoid UI.
