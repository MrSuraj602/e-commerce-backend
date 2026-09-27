# 🛒 E-Commerce Backend

A secure and scalable **RESTful backend for an e-commerce platform** built using **Spring Boot**. The application provides APIs for user authentication, product management, shopping cart, orders, reviews, ratings, addresses, and online payments.

## 🚀 Features

* 🔐 JWT-based authentication and authorization
* 👤 User registration and login
* 🛡️ Spring Security with role-based access control
* 📦 Product management
* 🗂️ Category-based product organization
* 🔎 Product filtering, sorting, and pagination
* 🛒 Shopping cart management
* 📋 Order creation and order management
* 📍 User address management
* ⭐ Product ratings and reviews
* 💳 Razorpay payment integration
* 👨‍💼 Admin product and order management
* 🔒 Password encryption using BCrypt
* ⚠️ Custom exception handling
* 🌐 RESTful API architecture
* 🗄️ MySQL database with JPA/Hibernate

---

## 🛠️ Tech Stack

### Backend

* Java
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate
* REST APIs
* JWT

### Database

* MySQL

### Payment

* Razorpay Payment Gateway

### Tools

* Maven
* Git
* GitHub
* Postman
* IntelliJ IDEA

---

## 🏗️ Project Architecture

The application follows a layered architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

### Main Layers

```text
src/main/java/
│
├── controller/
│   ├── AdminController
│   ├── AuthController
│   ├── CartController
│   ├── OrderController
│   ├── ProductController
│   ├── RatingController
│   ├── ReviewController
│   └── UserController
│
├── service/
│   ├── ProductService
│   ├── OrderService
│   ├── CartService
│   ├── UserService
│   └── ...
│
├── repository/
│   ├── ProductRepository
│   ├── OrderRepository
│   ├── CartRepository
│   └── ...
│
├── model/
│   ├── User
│   ├── Product
│   ├── Category
│   ├── Cart
│   ├── Order
│   ├── Review
│   ├── Rating
│   └── Address
│
├── security/
│   ├── JWT configuration
│   └── Security configuration
│
└── exception/
```

---

## 🔐 Authentication & Authorization

The application uses **Spring Security and JWT** for authentication.

### Authentication Flow

```text
User Login
    ↓
Spring Security
    ↓
Credentials Validation
    ↓
JWT Token Generated
    ↓
Client Stores Token
    ↓
Token Sent With Requests
    ↓
JWT Authentication Filter
    ↓
Protected API Access
```

Passwords are securely stored using **BCrypt password encoding**.

The application also provides different access levels for users and administrators.

---

## 💳 Razorpay Payment Integration

The backend integrates the **Razorpay Payment Gateway** for processing online payments.

Basic payment flow:

```text
Create Order
     ↓
Generate Razorpay Payment
     ↓
User Completes Payment
     ↓
Payment Verification
     ↓
Update Order / Payment Status
```

> Payment credentials should be configured using environment variables and should never be committed to GitHub.

---

## 🗄️ Database

The application uses **MySQL** with **JPA/Hibernate** for persistence.

Main entities include:

```text
User
 │
 ├── Address
 ├── Cart
 ├── Orders
 │
Product
 │
 ├── Category
 ├── Review
 └── Rating

Order
 │
 └── Order Items
```

---

## ⚙️ Getting Started

### Prerequisites

Make sure you have installed:

* Java 17+
* Maven
* MySQL
* Git

### 1. Clone the repository

```bash
git clone https://github.com/MrSuraj602/e-commerce-backend.git
cd e-commerce-backend
```

### 2. Create the database

Create a MySQL database:

```sql
CREATE DATABASE ecommerce;
```

### 3. Configure application properties

Create/update your application configuration with your local database credentials.

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
```

Configure your JWT and Razorpay credentials using environment variables rather than committing secrets to the repository.

### 4. Run the application

Using Maven:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

The backend will start on:

```text
http://localhost:8080
```

---

## 🔑 API Overview

The backend provides REST APIs for:

| Module         | Functionality                                      |
| -------------- | -------------------------------------------------- |
| Authentication | Registration and login                             |
| Users          | User profile and address management                |
| Products       | Product listing, filtering, sorting and pagination |
| Categories     | Product categorization                             |
| Cart           | Add, update and remove cart items                  |
| Orders         | Create and manage orders                           |
| Reviews        | Product reviews                                    |
| Ratings        | Product ratings                                    |
| Payments       | Razorpay payment processing                        |
| Admin          | Product and order management                       |

You can test the APIs using **Postman**.

---

## 🔮 Future Improvements

Planned improvements include:

* Personalized product recommendation system
* Redis caching
* Automated testing
* API documentation using Swagger/OpenAPI
* Docker containerization
* CI/CD pipeline
* Production deployment

---

## 👨‍💻 Author

**Suraj Rathod**

* GitHub: https://github.com/MrSuraj602
* LinkedIn: https://linkedin.com/in/surajrathod6/

---

## 📄 License

This project is developed for learning and portfolio purposes.
