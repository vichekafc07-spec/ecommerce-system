# Ecommerce System

A full-featured Ecommerce REST API built with Spring Boot following clean architecture principles and modern backend development practices.

## Features

### Authentication & Authorization

- JWT Authentication
- Spring Security
- Role-Based Access Control (RBAC)
- User Registration & Login
- Permission Management

### Product Management

- Category Management
- Product CRUD
- Product Image Upload
- Product Search
- Product Filtering
- Pagination

### Shopping Features

- Shopping Cart
- Wishlist
- Move Wishlist Item to Cart
- Product Reviews & Ratings

### Order Management

- Create Orders
- Order History
- Order Status Tracking
- Shipping Address Management

### Payment System

- Stripe Checkout Integration
- Stripe Webhook Handling
- Payment Status Management

### Email Notifications

- Welcome Email
- Order Confirmation Email
- Payment Success Email
- Order Status Update Email

### Dashboard & Reports

- Revenue Report
- Expense Report
- Profit Report
- Monthly Sales Analytics
- Top Selling Products
- Dashboard Statistics

---

## Tech Stack

### Backend

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- JWT

### Database

- PostgreSQL

### Additional Tools

- MapStruct
- Lombok
- Maven
- Stripe API
- Java Mail Sender

---

## Architecture

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

Project Structure

```text
src/main/java

├── auth
├── category
├── product
├── cart
├── wishlist
├── review
├── order
├── address
├── payment
├── dashboard
├── report
├── email
├── common
├── exception
├── security
└── config
```

---

## Database Design

Main Entities

```text
User
Role
Permission

Category
Product

Cart
CartItem

Wishlist
WishlistItem

Order
OrderItem

Address

Payment

Review
```

---

## API Modules

### Authentication

```http
POST /api/v1/auth/register
POST /api/v1/auth/login
POST /api/v1/auth/login
```

### Category

```http
GET    /api/v1/categories
POST   /api/v1/categories
PUT    /api/v1/categories/{id}
DELETE /api/v1/categories/{id}
```

### Product

```http
GET    /api/v1/products
GET    /api/v1/products/{id}
POST   /api/v1/products
PUT    /api/v1/products/{id}
DELETE /api/v1/products/{id}
PUT    /api/v1/products/{id}/image
```

### Cart

```http
POST   /api/v1/cart/add
GET    /api/v1/cart
PUT    /api/v1/cart/items/{id}
DELETE /api/v1/cart/items/{id}
```

### Wishlist

```http
POST   /api/v1/wishlist
GET    /api/v1/wishlist
DELETE /api/v1/wishlist/{id}
POST   /api/v1/wishlist/move-to-cart
```

### Orders

```http
POST   /api/v1/orders
GET    /api/v1/orders
GET    /api/v1/orders/{id}
```

### Payments

```http
POST /api/v1/payments/checkout/{orderId}
POST /api/v1/payments/webhook
```

### Reviews

```http
POST /api/v1/reviews
GET  /api/v1/products/{productId}/reviews
```

### Dashboard

```http
GET /api/v1/admin/dashboard

GET /api/v1/admin/dashboard/monthly-sales

GET /api/v1/admin/dashboard/top-products
```

### Reports

```http
GET /api/v1/admin/reports/financial
```

---

## Dashboard Analytics

### Financial Report

```json
{
  "revenue": 5000.00,
  "expense": 2800.00,
  "profit": 2200.00,
  "totalOrders": 120
}
```

### Monthly Sales

```json
[
  {
    "month": "JANUARY",
    "revenue": 1200
  },
  {
    "month": "FEBRUARY",
    "revenue": 1800
  }
]
```

### Top Selling Products

```json
[
  {
    "productId": 1,
    "productName": "Pepsi",
    "soldQuantity": 150
  }
]
```

---

## Running Locally

### Clone Repository

```bash
git clone https://github.com/vichekafc07-spec/ecommerce-system.git
```

### Configure Database

Update:

```properties
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
```

### Configure JWT

```properties
jwt.secret=
```

### Configure Stripe

```properties
stripe.secret-key=
stripe.webhook-secret=
```

### Configure Email

```properties
spring.mail.username=
spring.mail.password=
```

### Run Application

```bash
mvn spring-boot:run
```

---

## Future Improvements

- Redis Caching
- Docker Support
- KHQR Payment Integration
- CI/CD Pipeline
- Deployment
---

## Author

Built with Spring Boot by **SORL VICHIKA**
