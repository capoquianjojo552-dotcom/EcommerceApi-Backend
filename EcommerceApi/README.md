# EcommerceApi - Lab 8: DB Integration

## Database Schema
This project uses MySQL with 4 tables created by Hibernate via JPA entities.

*Tables:*
1. *products* - Stores product info
   - id BIGINT PK AUTO_INCREMENT
   - name VARCHAR(255) NOT NULL
   - description VARCHAR(255)
   - price DECIMAL(10,2) NOT NULL
   - stock INT
   - category_id BIGINT FK → categories.id

2. *categories* - Product categories
   - id BIGINT PK AUTO_INCREMENT
   - name VARCHAR(255) NOT NULL UNIQUE

3. *orders* - Customer orders
   - id BIGINT PK AUTO_INCREMENT
   - customer_name VARCHAR(255) NOT NULL
   - order_date DATETIME

4. *order_items* - Junction table for orders and products
   - id BIGINT PK AUTO_INCREMENT
   - quantity INT NOT NULL
   - order_id BIGINT FK → orders.id
   - product_id BIGINT FK → products.id

*Relationships:*
- *Category ↔ Product*: One-to-Many. One category has many products. Category.java uses @OneToMany(mappedBy="category"), Product.java uses @ManyToOne.
- *Order ↔ OrderItem*: One-to-Many. One order has many items. Order.java uses @OneToMany(mappedBy="order", cascade=ALL).
- *Product ↔ OrderItem*: One-to-Many. One product can be in many order items.

## API Endpoints
Base URL: http://localhost:8080/api

| Method | Endpoint | Description | Status Code |
| --- | --- | --- | --- |
| GET | /products | Get all products from DB | 200 OK |
| GET | /products/{id} | Get product by ID | 200 OK, 404 Not Found |
| POST | /products | Create new product | 201 Created, 400 Bad Request |
| PUT | /products/{id} | Update product | 200 OK, 404 Not Found |
| DELETE | /products/{id} | Delete product | 204 No Content |
| GET | /products/category/{name} | Filter by category name | 200 OK |
| GET | /products/search?min=0&max=1000 | Filter by price range | 200 OK |
| GET | /products/search/name?keyword=phone | Search by name | 200 OK |

## Setup Instructions
1. Create MySQL database: CREATE DATABASE ecommerce_db;
2. Update application.properties with your MySQL username/password
3. Run backend: ./gradlew bootRun
4. Open frontend/index.html with Live Server on port 5500
5. CORS is configured to allow http://localhost:5500

## Screenshots
[Note: Sorry sir  my browser console and my sql table screenshot are pending due to local environment constraints, however my code are complete]