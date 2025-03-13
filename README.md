# Barber Shop API

A REST API for managing a barber shop's client appointments and schedules.

## Overview

This Spring Boot application provides a backend system for barber shops to manage their client database and appointment scheduling. It includes features for client management, appointment scheduling, and comprehensive error handling.

## Tech Stack

- **Java** with **Spring Boot**
- **PostgreSQL** database
- **JPA/Hibernate** for ORM
- **Flyway** for database migrations
- **Swagger/OpenAPI** for API documentation
- **Lombok** for reducing boilerplate code

## Features

### Client Management
- Create, update, and delete client records
- List all clients
- Find clients by ID
- Validation for unique emails and phone numbers

### Appointment Scheduling
- Create and delete appointments
- View appointments by month
- Automatic conflict detection for overlapping appointments
- Association with client records

### API Response Standardization
- Consistent response formats for success and error scenarios
- HTTP status code compliance
- Detailed error messages

## Getting Started

### Prerequisites
- Java 17+
- PostgreSQL
- Maven

### Database Setup
1. Create a PostgreSQL database named `barber_shop`
2. Configure your database credentials in `application.yml`

```yaml
spring:
  datasource: 
    url: jdbc:postgresql://localhost:5432/barber_shop
    username: minion
    password: 1234
```

### Running the Application

```bash
# Clone the repository
git clone https://github.com/alekssandher/barber-shop-api.git

# Navigate to the project directory
cd barber-shop-api

# Run the application
mvn spring-boot:run
```

By default, the application will run on `http://localhost:8080`

## API Documentation

The API documentation is available via Swagger UI at `/swagger-ui.html` or a custom documentation page at `/docs.html` after starting the application.

### Main Endpoints

#### Client Endpoints
- `POST /clients` - Create a new client
- `POST /clients/{id}` - Update an existing client
- `DELETE /clients/{id}` - Delete a client
- `GET /clients` - List all clients
- `GET /clients/{id}` - Find client by ID

#### Schedule Endpoints
- `POST /schedule` - Create a new appointment
- `DELETE /schedule/{id}` - Delete an appointment
- `GET /schedule/{year}/{month}` - List appointments for a specific month

## Error Handling

The application includes comprehensive error handling with standardized error responses:

- `400 Bad Request` - Invalid input data
- `404 Not Found` - Resource not found
- `409 Conflict` - Resource already exists or conflict
- `500 Internal Server Error` - Server-side errors

## Database Schema

### Clients Table
```sql
CREATE TABLE CLIENTS (
    id BIGSERIAL not null primary key,
    name VARCHAR(150) not null,
    email VARCHAR(150) not null,
    phone BPCHAR(11) not null,
    CONSTRAINT UK_EMAIL UNIQUE (email),
    CONSTRAINT UK_PHONE UNIQUE (phone)
);
```

### Schedules Table
```sql
CREATE TABLE SCHEDULES (
    id BIGSERIAL not null primary key,
    start_at timestamp not null,
    end_at timestamp not null,
    client_id BIGSERIAL not null,
    CONSTRAINT UK_SCHEDULE_INTERVAL UNIQUE (start_at, end_at),
    CONSTRAINT FK_CLIENTS_SCHEDULES FOREIGN KEY(client_id) REFERENCES CLIENTS(id)
);
```

## Development

### Development Configuration

For development mode, you can use the `dev` profile:

```bash
mvn spring-boot:run -Dspring.profiles.active=dev
```

This enables additional features like:
- SQL query logging
- Formatted SQL output
- Hot reloading

## Project Structure

```
├── src/main/java/alekssandher/barber_shop_api
│   ├── config           # Application configuration
│   ├── controller       # API controllers
│   ├── dto              # Data transfer objects
│   ├── entity           # JPA entities
│   ├── exceptions       # Custom exceptions
│   ├── mappers          # Object mappers
│   ├── repository       # Data repositories
│   └── service          # Business logic
│       └── query        # Query services
├── src/main/resources
│   ├── db/migration     # Flyway migrations
│   └── static           # Static resources
└── src/test            # Test classes
```

## CORS Configuration

The API includes CORS configuration to allow cross-origin requests, making it ready for integration with frontend applications.

## License

This project is licensed under the MIT License - see the LICENSE file for details.
