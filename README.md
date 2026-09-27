# E-Commerce Backend

Spring Boot REST backend for the existing React storefront. It provides authentication, product catalog, cart, checkout/order, Razorpay payment, rating/review, profile, and administrative product/order APIs.

## Stack

- Java 21 and Spring Boot 4
- Spring MVC, Spring Security, Spring Data JPA, MySQL
- JWT bearer authentication
- Razorpay Java SDK
- springdoc-openapi 3.1.1 / Swagger UI
- JUnit 5, Mockito, MockMvc, and H2 for tests

## Run

Install a JDK 21 and configure a MySQL database named `ecommerce` (or supply another JDBC URL). From this directory:

```powershell
.\mvnw.cmd spring-boot:run
```

Configure these environment variables before starting. The local defaults are for development only; use a unique, protected JWT secret and deployment-managed credentials outside source control.

| Variable | Purpose |
| --- | --- |
| `DB_URL` | JDBC URL; defaults to `jdbc:mysql://localhost:3306/ecommerce` |
| `DB_USERNAME` | Database username; defaults to `root` |
| `DB_PASSWORD` | Database password |
| `JWT_SECRET` | HMAC signing secret, at least 32 bytes |
| `RAZORPAY_API_KEY` | Razorpay API key |
| `RAZORPAY_API_SECRET` | Razorpay API secret |

Never commit these values or place them in frontend source or documentation.

## Authentication And Roles

`POST /auth/signup` creates a customer account and returns a JWT. Signup always stores the `CUSTOMER` role; the request body cannot assign `ADMIN`. `POST /auth/signin` returns a JWT and the persisted role. Send the token as `Authorization: Bearer <JWT>` to protected APIs.

The backend converts the stored role to `ROLE_CUSTOMER` or `ROLE_ADMIN`, signs that authority into the JWT, and checks the authority in Spring Security. `/api/admin/**` requires `ROLE_ADMIN`; other `/api/**` routes require authentication. Existing accounts with no role are treated as customers. Admin accounts must be provisioned by a trusted database operator; there is no public role-promotion endpoint.

## API Modules

- Authentication: `/auth/signup`, `/auth/signin`
- Products: `/api/products`, `/api/products/{productId}`
- Cart and cart items: `/api/cart/**`, `/api/cart_items/**`
- Orders: `/api/orders/**`
- Payments: `/api/payments/**`
- Ratings and reviews: `/api/ratings/**`, `/api/reviews/**`
- Customer profile: `/api/users/profile`
- Admin products and orders: `/api/admin/products/**`, `/api/admin/orders/**`

Admin product APIs support create, update, list, and delete. Admin order APIs support listing, confirmation, shipping, delivery, cancellation, and deletion. Access is enforced by Spring Security, not only by frontend route checks.

Order statuses are the existing values `PENDING`, `PLACED`, `CONFIRMED`, `SHIPPED`, `DELIVERED`, and `CANCELLED`. After an administrator changes a status, customer order history (`GET /api/orders/user`) and order details (`GET /api/orders/{id}`) return the latest persisted value on the next fetch. Customers can retrieve only their own order details.

## Errors

Global exception handling returns JSON with `timestamp`, `status`, `error`, `message`, and `path`. Validation failures also include a `validationErrors` map keyed by field. Authentication and authorization failures use the same format.

## Swagger / OpenAPI

Start the backend, then open:

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

Use Swagger's Authorize button with the JWT returned by signup or sign-in.

## Tests

```powershell
.\mvnw.cmd test
```

Spring security integration tests use an in-memory H2 database. Service unit tests use JUnit 5 and Mockito to mock repositories and collaborator services; they do not connect to MySQL or call Razorpay.
