# 🛒 E-Commerce Backend

A secure and scalable **RESTful e-commerce backend** built with **Java 21 and Spring Boot 4**. The backend powers a React-based e-commerce application and provides authentication, product management, shopping cart, order processing, Razorpay payments, ratings, reviews, customer profiles, and administrative operations.

---

## 🚀 Features

### 🔐 Authentication & Authorization

* User registration and login
* JWT-based authentication
* Stateless authentication using Spring Security
* Role-based authorization
* `CUSTOMER` and `ADMIN` roles
* Secure password handling
* Protected customer and admin APIs
* Customer-specific order access

### 🛍️ Product Management

* Product listing and product details
* Product search/filtering
* Category-based product management
* Product creation, updating, and deletion
* Admin product management
* Support for product sizes and inventory-related information

### 🛒 Shopping Cart

* Add products to cart
* Update cart items
* Remove cart items
* Retrieve customer cart
* Cart item quantity management

### 📦 Order Management

* Create orders from cart items
* Customer order history
* Order details
* Address management
* Order status tracking
* Admin order management

Supported order statuses:

```text
PENDING
PLACED
CONFIRMED
SHIPPED
DELIVERED
CANCELLED
```

### 💳 Razorpay Payments

* Razorpay payment integration
* Payment link creation
* Payment verification
* Payment status management
* Order/payment status synchronization

### ⭐ Ratings & Reviews

* Product ratings
* Product reviews
* Customer-specific rating/review operations
* Rating and review persistence

### 👨‍💼 Admin Operations

Administrators can:

* Create products
* Update products
* Delete products
* View products
* View orders
* Confirm orders
* Ship orders
* Mark orders as delivered
* Cancel orders
* Delete orders

Admin APIs are protected at the backend using Spring Security and are not dependent only on frontend route protection.

### 🧪 Testing

The project includes:

* JUnit 5
* Mockito
* MockMvc
* Spring Security Test
* H2 database for integration/security testing
* Service-layer unit tests

---

## 🏗️ Architecture

The application follows a layered architecture:

```text
Client / React Frontend
          │
          ▼
     REST Controllers
          │
          ▼
       Services
          │
          ▼
     Repositories
          │
          ▼
        MySQL
```

Security and supporting components:

```text
Request
   │
   ▼
Spring Security
   │
   ├── JWT Validation
   ├── Authentication
   └── Role Authorization
   │
   ▼
Controller
   │
   ▼
Service
   │
   ▼
Repository
   │
   ▼
Database
```

---

## 🛠️ Technology Stack

### Backend

* **Java 21**
* **Spring Boot 4**
* Spring MVC
* Spring Security
* Spring Data JPA
* Hibernate
* Bean Validation
* Lombok

### Security

* JWT
* Spring Security
* JJWT

### Database

* MySQL
* H2 for testing

### Payments

* Razorpay Java SDK

### API Documentation

* SpringDoc OpenAPI
* Swagger UI

### Testing

* JUnit 5
* Mockito
* MockMvc
* Spring Security Test

### Build Tool

* Maven

---

## 📁 Project Structure

```text
src/
├── main/
│   ├── java/com/MrSuraj/eco/ecommerce/
│   │
│   ├── config/
│   │   ├── AppConfig.java
│   │   ├── JwtConstant.java
│   │   ├── JwtProvider.java
│   │   ├── JwtValidator.java
│   │   └── OpenApiConfig.java
│   │
│   ├── controller/
│   │   ├── AdminOrderController.java
│   │   ├── AdminProductController.java
│   │   ├── AuthController.java
│   │   ├── CartController.java
│   │   ├── CartItemController.java
│   │   ├── OrderController.java
│   │   ├── PaymentController.java
│   │   ├── ProductController.java
│   │   ├── RatingController.java
│   │   ├── ReviewController.java
│   │   └── UserController.java
│   │
│   ├── entity/
│   │   ├── User.java
│   │   ├── Product.java
│   │   ├── Category.java
│   │   ├── Cart.java
│   │   ├── CartItem.java
│   │   ├── Order.java
│   │   ├── OrderItems.java
│   │   ├── Address.java
│   │   ├── Rating.java
│   │   ├── Review.java
│   │   └── PaymentDetails.java
│   │
│   ├── repo/
│   │   ├── UserRepository.java
│   │   ├── ProductRepository.java
│   │   ├── CartRepository.java
│   │   ├── OrderRepository.java
│   │   ├── RatingRepository.java
│   │   └── ReviewRepository.java
│   │
│   ├── service/
│   │   ├── UserService.java
│   │   ├── ProductService.java
│   │   ├── CartService.java
│   │   ├── OrderService.java
│   │   ├── RatingService.java
│   │   └── ReviewService.java
│   │
│   ├── request/
│   ├── response/
│   └── Exception/
│
└── test/
    └── java/
        └── service/
            ├── CartServiceImplementationTest.java
            ├── ProductServiceImplementationTest.java
            ├── OrderServiceImplementationTest.java
            ├── CustomeUserServiceImplementationTest.java
            └── RatingReviewServiceImplementationTest.java
```

---

## 🔑 Authentication Flow

The application uses JWT-based authentication.

### Signup

```http
POST /auth/signup
```

A new user is registered as a `CUSTOMER`.

### Sign In

```http
POST /auth/signin
```

A successful login returns a JWT.

Use the token for protected requests:

```http
Authorization: Bearer <JWT>
```

The backend validates the JWT and extracts the user's role before allowing access to protected resources.

### Role-Based Access

```text
CUSTOMER
   │
   ├── Products
   ├── Cart
   ├── Orders
   ├── Payments
   ├── Ratings
   ├── Reviews
   └── Profile

ADMIN
   │
   ├── Product Management
   └── Order Management
```

Public signup does not allow a user to assign themselves the `ADMIN` role.

---

## 🔗 API Modules

| Module         | Base Endpoint            | Description                     |
| -------------- | ------------------------ | ------------------------------- |
| Authentication | `/auth/**`               | Signup and signin               |
| Products       | `/api/products/**`       | Product browsing and management |
| Cart           | `/api/cart/**`           | Shopping cart                   |
| Cart Items     | `/api/cart_items/**`     | Cart item operations            |
| Orders         | `/api/orders/**`         | Customer orders                 |
| Payments       | `/api/payments/**`       | Razorpay payment operations     |
| Ratings        | `/api/ratings/**`        | Product ratings                 |
| Reviews        | `/api/reviews/**`        | Product reviews                 |
| User Profile   | `/api/users/profile`     | Customer profile                |
| Admin Products | `/api/admin/products/**` | Admin product operations        |
| Admin Orders   | `/api/admin/orders/**`   | Admin order operations          |

For the complete request/response definitions, use the Swagger UI after starting the application.

---

## 📖 Swagger / OpenAPI

The backend provides interactive API documentation using Swagger/OpenAPI.

After starting the application:

### Swagger UI

```text
http://localhost:8080/swagger-ui/index.html
```

### OpenAPI JSON

```text
http://localhost:8080/v3/api-docs
```

You can use the **Authorize** button in Swagger UI to provide the JWT returned from signup/signin.

---

## ⚙️ Requirements

Before running the project, install:

* Java 21
* MySQL
* Maven

The project also includes the Maven Wrapper, so Maven does not need to be installed separately if you use:

```powershell
.\mvnw.cmd
```

---

## 🗄️ Database Setup

Create a MySQL database:

```sql
CREATE DATABASE ecommerce;
```

The application can use the default local database configuration or environment variables.

---

## 🔐 Environment Variables

Configure the following environment variables before starting the application:

| Variable              | Description         |
| --------------------- | ------------------- |
| `DB_URL`              | MySQL JDBC URL      |
| `DB_USERNAME`         | MySQL username      |
| `DB_PASSWORD`         | MySQL password      |
| `JWT_SECRET`          | JWT signing secret  |
| `RAZORPAY_API_KEY`    | Razorpay API key    |
| `RAZORPAY_API_SECRET` | Razorpay API secret |

Example:

```text
DB_URL=jdbc:mysql://localhost:3306/ecommerce
DB_USERNAME=root
DB_PASSWORD=your_password
JWT_SECRET=your_secure_jwt_secret
RAZORPAY_API_KEY=your_razorpay_key
RAZORPAY_API_SECRET=your_razorpay_secret
```

> **Important:** Never commit database passwords, JWT secrets, or Razorpay credentials to GitHub.

---

## ▶️ Running the Application

Clone the repository:

```bash
git clone <your-backend-repository-url>
```

Navigate into the project:

```bash
cd eco
```

Run the application:

### Windows

```powershell
.\mvnw.cmd spring-boot:run
```

### Linux / macOS

```bash
./mvnw spring-boot:run
```

The application will start on:

```text
http://localhost:8080
```

---

## 🧪 Running Tests

Run the complete test suite:

### Windows

```powershell
.\mvnw.cmd test
```

### Linux / macOS

```bash
./mvnw test
```

The project uses **H2** for test database scenarios, while service-level tests use **JUnit 5 and Mockito** to isolate business logic from external dependencies.

---

## 🛡️ Error Handling

The backend uses centralized exception handling through a global exception handler.

API errors return a structured response containing information such as:

```json
{
  "timestamp": "...",
  "status": 400,
  "error": "...",
  "message": "...",
  "path": "..."
}
```

Validation errors can additionally contain field-specific validation information.

This provides a consistent error format across the API.

---

## 📦 Order Lifecycle

Orders follow the application's defined lifecycle:

```text
PENDING
   │
   ▼
PLACED
   │
   ▼
CONFIRMED
   │
   ▼
SHIPPED
   │
   ▼
DELIVERED
```

An order can also be cancelled according to the application's order-management logic.

Administrators can update order status, while customers can retrieve their own order history and order details.

---

## 🔒 Security Highlights

* JWT-based stateless authentication
* Spring Security authorization
* Role-based access control
* Protected `/api/**` endpoints
* Admin-only `/api/admin/**` endpoints
* Customer ownership checks for order details
* Password encoding
* Validation of incoming requests
* Centralized exception handling
* Environment-based configuration for sensitive credentials

---

## 💳 Payment Flow

The payment integration uses Razorpay.

High-level flow:

```text
Customer places order
        │
        ▼
Backend creates payment request
        │
        ▼
Razorpay Payment
        │
        ▼
Payment verification
        │
        ▼
Payment status updated
        │
        ▼
Order status updated
```

Razorpay credentials are supplied through environment variables and should never be committed to source control.

---

## 🧪 Testing Strategy

The project contains tests for important service and security-related functionality.

Testing technologies include:

```text
JUnit 5
   +
Mockito
   +
MockMvc
   +
Spring Security Test
   +
H2
```

This allows business logic and security behavior to be tested without depending on the production MySQL database or making real Razorpay requests.

---

## 🔗 Frontend

This backend is designed to work with the React frontend of the E-Commerce application.

The frontend communicates with this backend through REST APIs and uses JWT authentication for protected operations.

**Frontend Repository:**
`<your-frontend-repository-url>`

---

## 📌 Future Improvements

Possible future enhancements include:

* Pagination for large product/order collections
* Product image storage through cloud storage
* Email notifications for order updates
* Advanced product search
* Inventory management improvements
* Production deployment and CI/CD
* API rate limiting
* Redis caching
* More comprehensive controller-level integration tests

---

## 👨‍💻 Author

**Suraj Rathod**

Java Full Stack Developer | Spring Boot | React | SQL

* GitHub: `https://github.com/MrSuraj602`
* LinkedIn: `https://linkedin.com/in/surajrathod6/`
* Portfolio: `https://suraj-r.netlify.app/`

---

## 📄 License

This project is developed for educational and portfolio purposes.
