#  Event Booking System — Backend (Spring Boot)

This repository contains the backend service for an Event Booking System built with Spring Boot. The application handles secure user authentication, event creation, category grouping, booking logic, and cloud-based media storage.
The backend is fully containerized using Docker and Docker Compose and follows a modular, entity-based project structure.

** A React frontend will be added later.

 Key Features
 User Authentication & Verification

JWT Authentication:
JWT tokens are issued during login to protect API endpoints.

Email Verification System:
New users receive an email verification token, and accounts remain disabled until confirmed.

* (Role-based access control is not yet implemented.)

# Cloud-Based Media Storage (Cloudinary)

Fully integrated Cloudinary service for storing:

Event posters

This supports fast cloud delivery and reduces local storage dependency.

#  Event & Booking Management

Admin/system owner (current default behavior) can:

Create, update, and delete events

Upload event posters

Assign categories

Set ticket limits, event details, and schedules

Users can:

Explore available events
Book events

# 📧 Email Notifications

Used for:
Account verification


# 🐳 Docker & Docker Compose

The backend is fully containerized:

Dockerfile for building the Spring Boot application image

Docker Compose setup connecting:

Backend container

Database container (MySQL/PostgreSQL)

Environment variables

Application networks

This ensures simple, consistent deployment on any machine.

🛠️ Tech Stack

Java 17+

Spring Boot

Spring Security (JWT)

Spring Data JPA

MySQL / PostgreSQL

Cloudinary API

JavaMailSender

Lombok

Docker & Docker Compose

# 📂 Modular Project Structure (Entity-Based Architecture)

The system follows a clean, entity-centric structure, where each entity has its own feature module.
Each entity folder typically contains:
<img width="1281" height="636" alt="image" src="https://github.com/user-attachments/assets/4b47fae0-d1b7-4fe2-b7a6-177cfe7d4c74" />

