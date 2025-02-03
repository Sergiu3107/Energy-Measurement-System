
# Energy Management System (EMS)

## Description

Energy Management System is a software application designed to help manage user data and devices for monitoring energy consumption. The system allows administrators to manage users and devices through CRUD operations, while users can view relevant information.

The application is developed using a microservices-based architecture, featuring two Spring Boot backend services—one for user management and another for device management. Each service connects to its own PostgreSQL database and is deployed using Docker. The frontend, developed in Angular, provides a modern and responsive interface for interacting with the backend services.

----------

## Technologies Used

-   **Backend:** Spring Boot (Java) for developing microservices.
-   **Database:** PostgreSQL for managing user and device data.
-   **Frontend:** Angular for creating the web interface.
-   **Deployment:** Docker and Docker Compose for container orchestration.
-   **Reverse Proxy:** Traefik for managing traffic between microservices.
-   **WebSockets:** Used for real-time communication, particularly in the Chat microservice.

----------

## Architecture

The application is organized into distinct layers:

-   **Controller Layer (API):** Exposes REST endpoints and handles HTTP requests.
-   **Service Layer:** Contains business logic for the application.
-   **Repository Layer:** Manages database interactions using JPA (Java Persistence API).
-   **Entity Layer:** Defines the entities used for data storage.

The system follows a microservices architecture, ensuring scalability and modularity.

----------

## Key Features

### 1. User Microservice

-   User management (create, delete, update, authenticate).
-   Role-based access (Administrator and Standard User).
-   Synchronization with the Device Microservice to manage user-device relationships.

### 2. Device Microservice

-   CRUD operations for managing devices.
-   Device-user association.
-   Synchronization with the User Microservice to maintain data integrity.

### 3. Monitoring Microservice

-   Collecting device metrics.
-   Alerting in case of anomalies or threshold breaches.
-   Generating reports on energy usage.

### 4. Chat Microservice

-   Real-time messaging between users using WebSockets.
-   User typing notifications.

### 5. Frontend Application

-   User and admin dashboards.
-   Device status visualization.
-   Authentication and authorization.

----------

## Docker

The application is containerized using Docker. Each microservice runs in an isolated container, orchestrated using Docker Compose.

### Docker-Compose Configuration:

-   **PostgreSQL**: Each microservice has its own PostgreSQL database container.
-   **User Microservice (user-ms)**: Communicates with the Device Microservice to manage user-device relationships.
-   **Device Microservice (device-ms)**: Handles device management.
-   **Chat Microservice (chat-ms)**: Manages real-time communication via WebSockets.
-   **Frontend (web-app)**: Provides the user interface.

### Network and Volumes:

-   Services are part of a shared Docker network for seamless communication.
-   Persistent Docker volumes ensure database data is retained.

----------

## Reverse Proxy

Traefik is used for traffic routing between services.

-   Automatic route configuration based on Docker labels.
-   Monitoring dashboard accessible to administrators.
-   Load balancing for improved scalability.

----------

## Running the Application

1.  Clone the repository:
    
    ```sh
    git clone <repository-url>
    cd EMS
    
    ```
    
2.  Start the services using Docker Compose:
    
    ```sh
    docker-compose up --build
    
    ```
    
3.  Access the application in a browser at `http://localhost`.
4.  Access the Traefik dashboard for monitoring at `http://localhost:8081`.

----------

## Useful Resources

-   [Spring Boot Documentation](https://www.baeldung.com/spring-boot)
-   [Docker Tutorial](https://www.docker.com/101-tutorial/)
-   [Angular Docs](https://angular.io/docs)