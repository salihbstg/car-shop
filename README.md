# 🚗 Car Marketplace

A microservice-based vehicle marketplace backend application built with Spring Boot, Spring Cloud, and PostgreSQL.

The platform enables users to register, authenticate using JWT, create and manage vehicle listings, upload vehicle images, and browse available listings. The project is designed using modern microservice architecture principles to demonstrate service discovery, API Gateway routing, secure authentication, inter-service communication, and cloud-based media management.

---

## 🏗️ Architecture

### Auth Service

Handles user registration, authentication, and JWT token generation.

### Customer Service

Manages customer information and profile operations.

### Car Service

Handles vehicle listing creation, retrieval, updating, and deletion.

### Media Service

Handles image uploads and integrates with Cloudinary for cloud-based media storage.

### API Gateway

Acts as the single entry point for all client requests and routes traffic to the appropriate services.

### Discovery Server

Provides service registration and discovery for all microservices.

---

## ✨ Features

* User Registration and Login
* JWT-Based Authentication and Authorization
* Vehicle Listing Management
* Vehicle Image Upload Support
* Cloudinary Integration
* Customer Profile Management
* Service Discovery with Eureka
* API Gateway Routing
* Service-to-Service Communication using OpenFeign
* RESTful API Design
* Global Exception Handling
* Request Validation

---

## 🛠️ Technologies

* Java 21
* Spring Boot
* Spring Security
* Spring Cloud Gateway
* Spring Cloud Netflix Eureka
* OpenFeign
* JWT
* PostgreSQL
* Cloudinary
* Maven

---

## 🎯 Project Goal

The primary goal of this project is to demonstrate modern backend development practices using microservice architecture, secure authentication, cloud-based media management, and service-to-service communication.

The project also serves as a foundation for full-stack development with a React frontend.

---

## 🔌 API Endpoints

### Auth Service

| Method | Endpoint       | Description                              |
| ------ | -------------- | ---------------------------------------- |
| POST   | /auth/register | Register a new user                      |
| POST   | /auth/login    | Authenticate user and generate JWT token |

---

### Customer Service

| Method | Endpoint                   | Description                  |
| ------ | -------------------------- | ---------------------------- |
| POST   | /api/v1/customers          | Create a customer            |
| GET    | /api/v1/customers          | Get all customers            |
| GET    | /api/v1/customers/{id}     | Get customer by ID           |
| GET    | /api/v1/customers/by-email | Get customer by email        |
| GET    | /api/v1/customers/by-phone | Check if phone number exists |

---

### Car Service

| Method | Endpoint                    | Description                 |
| ------ | --------------------------- | --------------------------- |
| POST   | /api/v1/cars                | Create a vehicle listing    |
| GET    | /api/v1/cars                | Get all vehicle listings    |
| GET    | /api/v1/cars/{id}           | Get vehicle by ID           |
| GET    | /api/v1/cars/by-customer-id | Get vehicles by customer ID |
| PATCH  | /api/v1/cars/{id}           | Update vehicle listing      |
| DELETE | /api/v1/cars/{id}           | Delete vehicle listing      |

---

### Media Service

| Method | Endpoint          | Description                |
| ------ | ----------------- | -------------------------- |
| POST   | /api/media/upload | Upload image to Cloudinary |
