Student Management System

A Spring Boot REST API for student management with JWT-based authentication, role-based authorization, BCrypt password encryption, SQLite database integration, validation, and global exception handling.

🚀 Features
Student registration
User login
JWT authentication
JWT token validation
Role-based authorization
Student dashboard
Admin-only endpoint
BCrypt password hashing
SQLite database
Spring Data JPA
Input validation
Global exception handling
Custom authentication error responses
Stateless session management
🛠️ Technologies Used
Java
Spring Boot
Spring Security
JWT
Spring Data JPA
Hibernate
SQLite
Maven
Jakarta Validation
REST API
📁 Project Structure
src/main/java/com/kv/studentmanagement/
│
├── config/
│   ├── JwtAuthenticationFilter.java
│   └── SecurityConfig.java
│
├── controller/
│   ├── AuthController.java
│   └── StudentController.java
│
├── dto/
│   ├── ApiErrorResponse.java
│   ├── AuthResponse.java
│   └── LoginRequest.java
│
├── entity/
│   └── User.java
│
├── exception/
│   └── GlobalExceptionHandler.java
│
├── repository/
│   └── UserRepository.java
│
├── security/
│   └── JwtAuthenticationEntryPoint.java
│
└── service/
    ├── AuthService.java
    ├── CustomUserDetailsService.java
    └── JwtService.java

🔐 Authentication Flow

The application uses JWT authentication.

The authentication flow is:

User
  │
  ▼
Register
  │
  ▼
SQLite Database
  │
  ▼
Login
  │
  ▼
AuthenticationManager
  │
  ▼
JWT Token Generated
  │
  ▼
Client sends JWT
  │
  ▼
JwtAuthenticationFilter
  │
  ▼
Token Validation
  │
  ▼
SecurityContext
  │
  ▼
Protected API

🌐 API Endpoints
1. Register Student

POST

/api/auth/register/student


Example request:

{
  "username": "student123",
  "password": "student1234",
  "email": "student@example.com"
}


Example response:

Student registered successfully inside SQLite database!

2. Login

POST

/api/auth/login


Example request:

{
  "username": "student123",
  "password": "student1234"
}


Example response:

{
  "token": "YOUR_JWT_TOKEN"
}


Copy the returned token and use it for protected endpoints.

3. Student Dashboard

GET

/api/students/dashboard


Authentication required.

Add the JWT token in the request header:

Authorization: Bearer YOUR_JWT_TOKEN


Example response:

{
  "message": "Welcome to the Student Dashboard!",
  "username": "student123",
  "authorities": [
    {
      "authority": "ROLE_STUDENT"
    }
  ]
}

4. Admin Settings

GET

/api/students/admin-settings


Requires:

ROLE_ADMIN


Students with ROLE_STUDENT receive:

403 Forbidden


An authenticated admin receives the protected admin response.

🔑 Roles and Authorization

The application currently supports role-based authorization.

Example student role:

ROLE_STUDENT


Admin role:

ROLE_ADMIN


The admin endpoint is protected using:

@PreAuthorize("hasRole('ADMIN')")


Method-level security is enabled using:

@EnableMethodSecurity

🗄️ Database

The application uses SQLite for local development.

Database configuration:

studentmanagement.db


The database contains the users table.

The User entity contains:

ID
Username
Password
Role
Email

Passwords are stored using BCrypt hashing rather than plain text.

The SQLite database file is excluded from Git using .gitignore.

⚙️ Configuration

The application runs on:

http://localhost:9094


Important configuration includes:

server.port=9094
spring.datasource.url=jdbc:sqlite:studentmanagement.db
spring.jpa.hibernate.ddl-auto=update
jwt.expiration=86400000


Never commit real passwords, API keys, JWT secrets, database credentials, or other sensitive information to GitHub.

▶️ How to Run
Prerequisites

Install:

Java
Maven
Git
Clone the Repository
git clone YOUR_GITHUB_REPOSITORY_URL


Navigate into the project:

cd student-management-system

Run the Application

Using Maven:

mvn spring-boot:run


The application will start on:

http://localhost:9094

🧪 Testing with Postman

You can test the API using Postman.

Recommended testing order:

1. Register Student
       ↓
2. Login
       ↓
3. Copy JWT Token
       ↓
4. GET /api/students/dashboard
       ↓
5. Test Admin Endpoint

Test Without JWT

Request:

GET /api/students/dashboard


without an Authorization header.

Expected response:

401 Unauthorized

Test Invalid JWT

Send:

Authorization: Bearer invalid-token


Expected response:

401 Unauthorized

Test Student Access to Admin Endpoint

Login as a student and call:

GET /api/students/admin-settings


Expected response:

403 Forbidden

🔒 Security

This project demonstrates several Spring Security concepts:

Stateless authentication
JWT Bearer authentication
Password hashing with BCrypt
Role-based authorization
Custom authentication entry point
JWT expiration handling
Invalid JWT handling
Method-level authorization
📌 Future Improvements

Possible future improvements include:

Admin registration/management
Student CRUD operations
Refresh tokens
JWT secret configuration through environment variables
Pagination
Search and filtering
API documentation using Swagger/OpenAPI
Unit and integration tests
Docker support
Production database such as PostgreSQL or MySQL
👨‍💻 Author

Your Name

GitHub: YOUR_GITHUB_PROFILE_URL

⭐ Project Purpose

This project was developed to practice building a secure REST API using Spring Boot, Spring Security, JWT authentication, role-based authorization, JPA, and SQLite.
