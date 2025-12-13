# Project: Diabetes Management Main BE

## Project Context

This project is a Spring Boot application written in Java, designed to serve as the backend for a diabetes management system. It utilizes a PostgreSQL database and manages the database schema through Flyway migrations. The project is built using Gradle and follows modern RESTful API design principles.

## Core Architectural Patterns

-   **Resource-Oriented Controllers**: Controllers are organized by the resource they manage (e.g., `PatientController`, `PhysicianController`, `LogController`). This promotes a clean, RESTful API structure.
-   **Service Layer**: Business logic is encapsulated within service classes (e.g., `DashboardService`, `LogService`). Services are responsible for orchestrating data access and performing complex operations.
-   **Data Access Objects (DAO)**: Data access is handled by repositories that extend a common `AbstractRepository`. This ensures consistency and reduces boilerplate code.
-   **Data Transfer Objects (DTOs)**: DTOs are used to transfer data between the client and the server. They are organized by resource and feature within the `controller` package (e.g., `controller/patient/dto/dashboard`).
-   **UUIDs as Primary Keys**: All entities use `UUID` as their primary key type. This is a critical convention to follow.

## Project Structure

The project follows a standard Maven directory layout:

-   `src/main/java`: Contains the Java source code.
    -   `dm/diabetesmanagementmainbe`: The root package.
        -   `DiabetesManagementMainBeApplication.java`: The main entry point of the Spring Boot application.
        -   `config/`: Contains configuration classes for security, etc.
        -   `controller/`: Contains the REST API controllers, organized by resource (`auth`, `patient`, `physician`).
            -   `dto/`: DTOs are located within sub-packages of the controller, organized by feature.
        -   `service/`: Contains the business logic, organized by resource (`auth`, `patient`, `user`).
        -   `dao/`: Contains the data access objects.
            -   `model/`: Contains the JPA entities, organized by feature (`user`, `logging`, `tracker`, `settings`, `communication`).
            -   `repository/`: Contains the Spring Data JPA repositories, organized by feature.
-   `src/main/resources`: Contains the application resources.
    -   `application.yml`: The main application configuration file.
    -   `db/migration/`: Contains the Flyway database migration scripts.
-   `build.gradle`: The Gradle build file.
-   `README.md`: The project's README file.

## Agent-focused guidance

When working on this project, please adhere to the following guidelines:

-   **Follow Existing Patterns**: When adding new features, strictly follow the established architectural patterns. New endpoints should be added to the appropriate resource-oriented controller, business logic to a service, and so on.
-   **Create Flyway Migrations**: Any changes to the database schema **must** be accompanied by a new Flyway migration script in `src/main/resources/db/migration/`. Use a new timestamp in the filename to ensure it runs after existing migrations.
-   **Update DTOs and Entities**: When changing the database schema, ensure that the corresponding JPA entities and DTOs are updated accordingly.
-   **Use UUIDs for IDs**: Always use `UUID` as the data type for primary keys in entities, repositories, services, and controllers.
-   **Maintain Consistency**: Ensure that the logic of files, structure, and architecture of any modifications are consistent with the existing codebase. Create services, controllers, and endpoints that follow the established model and DTOs concept.
