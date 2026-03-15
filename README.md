# Homework #4: Selenoid, Ansible & Citrus Integration

This project demonstrates a production-grade test automation setup using **Selenoid** for browser orchestration, **Ansible** for infrastructure provisioning, **Citrus Framework** for integration testing, and **Chrome Mobile Emulation**.

---

## Prerequisites

Before running the project, ensure the following are installed:

- Docker / Docker Desktop
- Java 17+ (OpenJDK 25 was used during development)
- Maven
- Ansible

---

## Infrastructure as Code

The project includes **Ansible automation** for preparing and running the Selenoid infrastructure.

Main Ansible files:

- `ansible/deploy.yml` — main playbook
- `ansible/inventory.ini` — inventory configuration
- `ansible/roles/playbook-selenoid/roles/docker` — Docker installation and setup
- `ansible/roles/playbook-selenoid/roles/rendering_configs` — generation of Selenoid, GGR, Nginx, and quota configuration files
- `ansible/roles/playbook-selenoid/roles/docker-compose` — Docker Compose-based container startup and validation

---

## Generated Infrastructure Files

During execution, the playbook generates the required infrastructure files under:

- `/root/selenoid/browsers.json` — browser configuration for Selenoid
- `/root/selenoid/docker-compose.yaml` — Docker Compose file for infrastructure startup
- `/root/selenoid/selenoid.conf` — Nginx proxy configuration
- `/root/selenoid/grid-router/quota/test.xml` — GGR quota configuration

Template files are stored in:

- `ansible/roles/playbook-selenoid/roles/rendering_configs/templates/browsers.json.j2`
- `ansible/roles/playbook-selenoid/roles/rendering_configs/templates/docker-compose.yml.j2`
- `ansible/roles/playbook-selenoid/roles/rendering_configs/templates/selenoid.conf.j2`
- `ansible/roles/playbook-selenoid/roles/rendering_configs/templates/test.xml.j2`

---

## Selenoid Setup and Launch

Selenoid infrastructure is deployed using **Ansible**.

From the project root, run:

```bash
ansible-playbook -K -i ansible/inventory.ini ansible/deploy.yml
```

If the environment already has passwordless sudo configured, the playbook can also be run without `-K`:

```bash
ansible-playbook -i ansible/inventory.ini ansible/deploy.yml
```

> Note: in some environments Ansible may request a sudo/become password because the playbook performs system-level actions such as Docker installation, service configuration, and writing generated files under privileged locations.

The playbook performs the following steps:

- installs and configures Docker
- generates all required Selenoid, GGR, and Nginx configuration files
- creates Docker networks
- starts the infrastructure using Docker Compose
- verifies the container state

---

## Verify Selenoid is Running

Check the Selenoid/GGR endpoint:

```bash
curl http://localhost/wd/hub/status
```

Expected result: a JSON response with `"ready": true`.

You can also inspect running containers:

```bash
docker ps -a
```

Or check the compose stack directly:

```bash
docker compose -f /root/selenoid/docker-compose.yaml ps
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
WebDriver instances connect to Selenoid through **Nginx + GGR** at:

```text
http://localhost/wd/hub
```

### Citrus Framework
Tests are implemented using `CitrusExtension`, enabling validation, reporting, and test orchestration.

### Mobile Testing
Chrome Mobile Emulation is configured using the **Nexus 5** device profile to satisfy mobile browser testing requirements.

### Stability Configuration
Selenoid session timeouts are configured to ensure stable execution during browser startup and test initialization.

---

## Performance Notes

A short delay during test startup is expected due to initialization of two dependency injection systems:

- **Citrus Context** — reporting, validation, and orchestration
- **Google Guice** — dependency injection for Page Objects and WebDriver instances

This architecture provides modularity and extensibility while maintaining stable execution.

---

## Project Structure

```text
ansible/
  deploy.yml
  inventory.ini
  roles/playbook-selenoid/roles/
    docker/
    docker-compose/
    rendering_configs/

src/
pom.xml
README.md
```

---

## Summary

This homework demonstrates:

- automated infrastructure provisioning with **Ansible**
- browser orchestration with **Selenoid**
- request routing through **Nginx + GGR**
- remote UI test execution with **Selenium / Selenide**
- integration testing with **Citrus Framework**
- mobile browser emulation with **Chrome Mobile Emulation**
