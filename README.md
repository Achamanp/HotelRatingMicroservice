# HotelRatingMicroservice

A Spring Boot–based Hotel Rating System designed using resilient microservice patterns and secure, scalable architecture.

✨ Features
Rate Limiter – Controls the number of incoming requests to protect the service from spamming and overloading.

Circuit Breaker – Ensures system stability by gracefully handling failures and preventing cascading breakdowns.

Retry Mechanism – Automatically retries failed requests with fallback logic to improve reliability.

JWT Security – Implements JSON Web Token–based authentication and authorization to secure all APIs.

RESTful APIs – Cleanly designed endpoints for creating, viewing, and managing hotel ratings.

Spring Cloud Integration – Built to work seamlessly in a microservice ecosystem.

🛠️ Tech Stack
Java 17

Spring Boot

Spring Cloud Resilience4j

Spring Security (JWT)

Maven

MySQL

Docker (optional for containerization)

🚀 Getting Started
bash
Copy
Edit
# Clone the project
git clone https://github.com/your-username/HotelRatingMicroservice.git

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
🔐 Authentication
All protected endpoints require a valid JWT token in the Authorization header:

makefile
Copy
Edit
Authorization: Bearer <token>
Tokens must be obtained using the /auth/login endpoint (username & password).

📡 Resilience Patterns Used
Pattern	Library	Purpose
Rate Limiter	Resilience4j	Throttles excessive requests
Circuit Breaker	Resilience4j	Stops calling failing services
Retry	Resilience4j	Retries failed calls automatically

📁 API Overview
GET /ratings – Get all ratings

POST /ratings – Create a new rating (secured)

GET /ratings/{id} – Fetch rating by ID

GET /ratings/hotel/{hotelId} – Get rating by hotel

GET /ratings/user/{userId} – Get rating by user

📌 Future Enhancements
Service discovery (Eureka/Consul)

API Gateway integration

Swagger documentation

CI/CD pipeline
