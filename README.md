# EOrganization Project Portfolio

This is an initial Spring Boot project skeleton for a Project Portfolio management system called EOrganization with:
- JWT (access + refresh tokens)
- Roles and permissions (fine-grained)
- Spring Security
- PostgreSQL
- Swagger/OpenAPI
- DTOs, services, repositories
- Global exception handling

## How to run

1. Build with Maven:
```
mvn clean package
```

2. Run PostgreSQL (recommended via docker-compose):
```
docker-compose up -d
```

3. Start the app:
```
java -jar target/eorganization-portfolio-0.0.1-SNAPSHOT.jar
```

## Auth endpoints
- `POST /api/auth/register` - register user
- `POST /api/auth/login` - login (returns access + refresh tokens)
- `POST /api/auth/refresh` - refresh access token using refresh token
- `POST /api/auth/logout` - revoke refresh token

Swagger UI: http://localhost:8080/swagger-ui.html

> Default JWT secret is in `application.yml`. Replace it before production.
