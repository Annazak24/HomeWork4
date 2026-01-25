# Homework #4: Selenoid & Citrus Integration

This project demonstrates a production-grade test automation setup using **Selenoid** for browser orchestration, **Citrus Framework** for integration testing, and **Chrome Mobile Emulation**.

## Prerequisites
Before running the tests, ensure you have the following installed:
* **Docker** & **Docker Desktop**
* **Java 17+** (OpenJDK 25 used in development)
* **Maven**

---

## 1. Selenoid Configuration and Launch

The project includes a `selenoid` directory in the root folder containing the `browsers.json` configuration.

### Start Selenoid Hub
Open your terminal (PowerShell or Bash) in the project root and run:

```powershell
docker run -d                                   `
    --name selenoid                             `
    -p 4444:4444                                `
    -v //var/run/docker.sock:/var/run/docker.sock `
    -v "${PWD}/selenoid:/etc/selenoid:ro"       `
    aerokube/selenoid:latest-release
Start Selenoid UI (Optional)
To visualize the test execution in real-time via VNC:

PowerShell
docker run -d           `
    --name selenoid-ui  `
    -p 8080:8080        `
    --link selenoid     `
    aerokube/selenoid-ui:latest-release --selenoid-uri http://selenoid:4444
2. Running the Tests
The tests can be executed via IntelliJ IDEA or using Maven.

To run via Maven in Remote (Selenoid) mode:

Bash
mvn test -Drun.type=remote -Dbrowser.name=chrome
3. Project Implementation Details
Selenoid Hub: The WebDriverFactory connects to the hub at http://localhost:4444/wd/hub.

Citrus Framework: Tests are integrated with CitrusExtension to leverage Citrus's validation and reporting capabilities.

Mobile Emulation: The factory applies Chrome Mobile Emulation using the Nexus 5 device profile, fulfilling the mobile testing requirement.

Stability: A sessionTimeout of 15 minutes is configured to ensure the Selenoid session remains active during the initial setup phase.

4. Performance Note
You may notice a slight delay during the startup of the tests. This is a known technical trade-off caused by the initialization of two separate Dependency Injection systems:

Citrus Context: Initializes heavy reporting and coordination beans.

Google Guice: Manages the injection of Page Objects and WebDriver instances.

The combination of these frameworks provides high modularity and powerful validation but requires extra time for the Application Context to load. The environment has been optimized with increased timeouts to ensure reliability.

5. Visualizing Tests
Once the tests start, you can navigate to http://localhost:8080 to see the mobile browser emulation in action via the VNC console.
