# Lab 9: Spring Security + Session-Based Authentication + Validation

## 1. Security Architecture

*Session-Based Authentication Flow:*
1. User logs in via POST /login with username/password.
2. Spring Security validates credentials using CustomUserDetailsService + BCryptPasswordEncoder.
3. On success, server creates an HttpSession and sends back a JSESSIONID cookie.
4. Browser automatically includes JSESSIONID on every subsequent request.
5. Server uses the cookie to retrieve the user from SecurityContext and authorize requests.
6. *CSRF Protection* is enabled by default. All POST/PUT/DELETE requests require a CSRF token to prevent cross-site attacks.

## 2. Validation Rules

Applied using jakarta.validation annotations on DTOs:

| Entity | Field | Constraints |
| --- | --- | --- |
| *User* | username | @NotBlank, @Size(min=8, max=20) |
| *User* | password | @NotBlank, @Size(min=8) |
| *Product* | name | @NotBlank |
| *Product* | price | @NotNull, @Positive |
| *Product* | stock | @Min(0) |

Validation errors return a 400 Bad Request with a structured JSON response handled by GlobalExceptionHandler.

### 3. API Reference

| Endpoint | Method | Auth Required | Role | Description |
| --- | --- | --- | --- | --- |
| /api/v1/auth/register | POST | No | - | Register new user with hashed password |
| /login | POST | No | - | Form login, creates JSESSIONID cookie |
| /logout | POST | Yes | Any | Invalidates session + deletes cookie |
| /api/v1/auth/me | GET | Yes | Any | Get current authenticated user info |
| /api/v1/products | GET | No | - | List all products, public access |
| /api/v1/products | POST | Yes | ADMIN | Create new product |
| /api/v1/products/{id} | PUT | Yes | ADMIN | Update existing product |
| /api/v1/products/{id} | DELETE | Yes | ADMIN | Delete product |
| /api/v1/orders | POST | Yes | USER | Create new order for logged-in user |