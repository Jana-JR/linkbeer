# URL Shortener - Spring Boot

## Overview
This project is a URL Shortener web application built with Spring Boot. It allows users to register, log in, shorten URLs, track analytics for their links, and manage their own shortened URLs. The application uses JWT-based authentication and supports analytics for link clicks.

## Features
- User registration and authentication (JWT-based)
- Shorten long URLs to short, unique codes
- Redirect from short URL to original URL
- Track and display analytics (click counts, date-based stats)
- User dashboard to manage and view their URLs
- RESTful API endpoints secured for authenticated users

## Technology Stack
- Java 17+
- Spring Boot 3.5.x
- Spring Security (JWT)
- Spring Data JPA (MySQL)
- Lombok
- JJWT (JSON Web Token)
- Maven

## Workflow
### 1. User Registration & Authentication
- User registers via `POST /api/auth/register`.
- User logs in via `POST /api/auth/login` and receives a JWT token.
- JWT token is used in the `Authorization: Bearer <token>` header for all protected endpoints.

### 2. Shortening a URL
- Authenticated user sends a `POST /api/urls/shorten` request with the original URL.
- The system generates a unique 8-character short code and stores the mapping in the database, associated with the user.
- The response contains the short URL and metadata.

### 3. Redirecting
- When a user visits `GET /{shortUrl}`, the system looks up the original URL.
- If found, it increments the click count, records a click event, and redirects (HTTP 302) to the original URL.
- If not found, returns 404.

### 4. Managing URLs
- Authenticated users can list their URLs via `GET /api/urls/myurls`.
- Each entry includes the original URL, short code, click count, and creation date.

### 5. Analytics
- Authenticated users can request analytics for a short URL via `GET /api/urls/analytics/{shortUrl}?startDate=...&endDate=...`.
- The system returns click counts grouped by date for the specified range.

### 6. Security
- All `/api/urls/**` endpoints require a valid JWT token.
- Only the owner of a URL can view its analytics or manage it.

## Getting Started
### Prerequisites
- Java 17 or newer
- Maven
- MySQL running locally (default: `root` user, no password, database: `urlShortner`)

### Configuration
Edit `src/main/resources/application.properties` as needed:
```
spring.datasource.url=jdbc:mysql://localhost:3306/urlShortner
spring.datasource.username=root
spring.datasource.password=
jwt.secret=... # already set
jwt.expiration=172800000
frontend.url=http://localhost:5173
```

### Build & Run
```pwsh
./mvnw clean package
java -jar target/url-shortner-sb-0.0.1-SNAPSHOT.jar
```
The application will start on [http://localhost:8080](http://localhost:8080).

## API Endpoints
- `POST /api/auth/register` — Register a new user
- `POST /api/auth/login` — Authenticate and receive JWT
- `POST /api/urls/shorten` — Shorten a URL (authenticated)
- `GET /api/urls/myurls` — List user's URLs (authenticated)
- `GET /api/urls/analytics/{shortUrl}?startDate=...&endDate=...` — Analytics for a short URL (authenticated)
- `GET /{shortUrl}` — Redirect to the original URL

## Analytics
- Each redirect increments the click count and records a click event.
- Analytics endpoint provides click counts grouped by date.

## Security
- JWT tokens are required for all `/api/urls/**` endpoints.
- Use the `Authorization: Bearer <token>` header.

## Testing
Run tests with:
```pwsh
./mvnw test
```

## Project Structure
- `controller/` — REST controllers for authentication, URL management, and redirection
- `service/` — Business logic for users, URLs, analytics
- `repository/` — Spring Data JPA repositories
- `models/` — Entity classes for User, UrlMapping, ClickEvent
- `security/` — JWT utilities and security configuration

## Dependencies
Key dependencies (see `pom.xml`):
- `spring-boot-starter-web`
- `spring-boot-starter-data-jpa`
- `spring-boot-starter-security`
- `io.jsonwebtoken:jjwt-api`
- `mysql-connector-j`
- `lombok`

## Contributing
- Fork the repository and submit pull requests.
- Issues and feature requests are welcome.
- All commits must be signed off (see DCO).

## License
This project is licensed under the Apache License 2.0.
