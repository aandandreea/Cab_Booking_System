# Cab Booking System

A REST API for a cab booking system, built with Spring Boot. It manages customers, cabs, drivers, and bookings, including business logic for cab availability.

## Tech Stack

- Java 21
- Spring Boot 4.1.1
- Spring Data JPA / Hibernate 7.4.5
- PostgreSQL 17.5
- Maven

## Entities

- **Customer** — customers who make bookings
- **Cab** — cabs, with status `AVAILABLE` / `BOOKED`
- **Driver** — drivers, each optionally linked to a cab
- **Booking** — bookings, with status `CONFIRMED` / `COMPLETED` / `CANCELLED`


## Running the app

```bash
./mvnw spring-boot:run
```

The application starts on `http://localhost:8080`.

## Running with Docker

The app can also be run containerized, together with a PostgreSQL database, using Docker Compose:

This starts two services:

- `app` — the Spring Boot application, built from the `Dockerfile` (multi-stage build: Maven/JDK to compile, then a slim JRE image to run), exposed on `http://localhost:8080`
- `db` — a PostgreSQL 17 container, exposed on `localhost:5433` (mapped from the container's `5432`, to avoid clashing with a local Postgres install)


## Endpoints

### Customers — `/api/customers`

| Method | Path | Description |
|---|---|---|
| POST | `/api/customers` | Register a new customer |
| GET | `/api/customers` | List all customers |
| GET | `/api/customers/{id}` | Get customer details |
| PUT | `/api/customers/{id}` | Update name/phone number |
| DELETE | `/api/customers/{id}` | Delete customer |

### Cabs — `/api/cabs`

| Method | Path | Description |
|---|---|---|
| POST | `/api/cabs` | Add a new cab (default status `AVAILABLE`) |
| GET | `/api/cabs` | List all cabs |
| GET | `/api/cabs/availableCabs` | List only available cabs |
| GET | `/api/cabs/{id}` | Get cab details |
| PUT | `/api/cabs/{id}` | Update registration number/model |
| DELETE | `/api/cabs/{id}` | Delete cab |

### Drivers — `/api/drivers`

| Method | Path | Description |
|---|---|---|
| POST | `/api/drivers` | Register a new driver |
| GET | `/api/drivers` | List all drivers |
| GET | `/api/drivers/{id}` | Get driver details |
| PUT | `/api/drivers/{id}` | Update name/phone number |
| DELETE | `/api/drivers/{id}` | Delete driver |

### Bookings — `/api/bookings`

| Method | Path | Description |
|---|---|---|
| POST | `/api/bookings` | Create a booking (checks cab availability, marks the cab as `BOOKED`) |
| GET | `/api/bookings` | List all bookings |
| GET | `/api/bookings/{id}` | Get booking details |
| PUT | `/api/bookings/{id}/complete?fare={value}` | Complete the booking, set the fare, free the cab |
| PUT | `/api/bookings/{id}/cancel` | Cancel the booking, free the cab |
| DELETE | `/api/bookings/{id}` | Delete booking |

## Validation and errors

All create/update request fields are validated (`@NotBlank`, `@Size`, `@Email`, `@NotNull`). Errors are returned in a consistent format:

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "name: Name is required.",
  "path": "/api/customers",
  "timestamp": "2026-10-26T14:00:00"
}
```

Status codes used:

- `201 Created` — resource created successfully
- `200 OK` — successful read/update
- `204 No Content` — successful delete
- `400 Bad Request` — invalid data
- `404 Not Found` — resource not found
- `409 Conflict` — duplicate email/license number, or cab not available
