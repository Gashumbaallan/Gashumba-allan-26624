# E-Commerce Product API - Complete Implementation

## Project Overview
This is a Spring Boot REST API for an e-commerce product catalog with full CRUD operations and advanced search/filter capabilities.

## Project Structure

```
src/
├── main/
│   ├── java/question4/question4_E_Commerce_api/
│   │   ├── Question4ECommerceApiApplication.java (Main Application)
│   │   ├── controller/
│   │   │   └── ProductController.java (All REST endpoints)
│   │   ├── model/
│   │   │   └── Product.java (Product entity with 7 fields)
│   │   └── service/
│   │       └── ProductService.java (Business logic & in-memory storage)
│   └── resources/
│       └── application.properties
└── test/
    └── java/question4/question4_E_Commerce_api/
        └── Question4ECommerceApiApplicationTests.java (Comprehensive tests)
```

## Product Model

The `Product` class includes all required fields:
- **productId** (Long) - Unique identifier
- **name** (String) - Product name
- **description** (String) - Product description
- **price** (Double) - Product price
- **category** (String) - Product category (e.g., Electronics, Footwear)
- **stockQuantity** (int) - Available stock
- **brand** (String) - Manufacturing brand

## API Endpoints

### 1. **GET /api/products** - Get all products with pagination
- **Parameters:**
  - `page` (optional, default: 1) - Page number
  - `limit` (optional, default: 10) - Items per page
- **Response:** Returns paginated list with total count
- **Status Code:** 200 OK

### 2. **GET /api/products/{productId}** - Get product by ID
- **Parameters:** productId (path variable)
- **Response:** Complete product details or error message
- **Status Codes:** 200 OK, 404 NOT FOUND

### 3. **GET /api/products/category/{category}** - Get products by category
- **Parameters:** category (path variable)
- **Response:** List of products in the specified category
- **Status Code:** 200 OK

### 4. **GET /api/products/brand/{brand}** - Get products by brand
- **Parameters:** brand (path variable)
- **Response:** List of products from the specified brand
- **Status Code:** 200 OK

### 5. **GET /api/products/search** - Search products
- **Parameters:** `keyword` (query parameter, required)
- **Functionality:** Searches in product name and description (case-insensitive)
- **Response:** List of matching products
- **Status Codes:** 200 OK, 400 BAD REQUEST (if keyword missing)

### 6. **GET /api/products/price-range** - Get products within price range
- **Parameters:**
  - `min` (query parameter, required) - Minimum price
  - `max` (query parameter, required) - Maximum price
- **Response:** Products with prices between min and max
- **Status Codes:** 200 OK, 400 BAD REQUEST (invalid range)

### 7. **GET /api/products/in-stock** - Get in-stock products
- **Functionality:** Returns products with stockQuantity > 0
- **Response:** List of available products
- **Status Code:** 200 OK

### 8. **POST /api/products** - Create new product
- **Request Body:** Product JSON (productId can be null, auto-generated)
- **Validation:**
  - Name is required
  - Price must be non-negative
- **Response:** Created product with auto-generated ID
- **Status Codes:** 201 CREATED, 400 BAD REQUEST

### 9. **PUT /api/products/{productId}** - Update product
- **Parameters:** productId (path variable)
- **Request Body:** Updated product details
- **Validation:**
  - Name is required
  - Price must be non-negative
- **Response:** Updated product
- **Status Codes:** 200 OK, 404 NOT FOUND, 400 BAD REQUEST

### 10. **PATCH /api/products/{productId}/stock** - Update stock
- **Parameters:**
  - productId (path variable)
  - quantity (query parameter, required)
- **Validation:** Quantity must be non-negative
- **Response:** Updated stock information
- **Status Codes:** 200 OK, 404 NOT FOUND, 400 BAD REQUEST

### 11. **DELETE /api/products/{productId}** - Delete product
- **Parameters:** productId (path variable)
- **Response:** Confirmation message
- **Status Codes:** 200 OK, 404 NOT FOUND

## Initial Data

The system initializes with 12 products across 5 categories:

1. **Electronics:**
   - iPhone 15 Pro ($999.99) - Apple - 50 units
   - Samsung Galaxy S24 ($899.99) - Samsung - 45 units
   - iPad Air ($599.99) - Apple - 35 units
   - Sony WH-1000XM5 Headphones ($399.99) - Sony - 80 units

2. **Footwear:**
   - Nike Air Max 90 ($129.99) - Nike - 120 units
   - Adidas Ultraboost 22 ($189.99) - Adidas - 95 units

3. **Photography:**
   - Canon EOS R5 ($3899.99) - Canon - 15 units
   - Sony A7R V ($3198.99) - Sony - 20 units

4. **Home Appliances:**
   - Samsung 65 inch QLED TV ($1299.99) - Samsung - 25 units
   - LG Refrigerator ($1799.99) - LG - 10 units

5. **Computers:**
   - Dell XPS 13 ($999.99) - Dell - 30 units
   - MacBook Pro 16 ($2499.99) - Apple - 20 units

## Test Coverage

The test class includes comprehensive tests for:
- ✅ Getting all products with pagination
- ✅ Getting single product by ID (success and not found cases)
- ✅ Filtering by category (existing and empty)
- ✅ Filtering by brand
- ✅ Searching with keyword (case-insensitive)
- ✅ Searching with missing keyword (error handling)
- ✅ Price range filtering (valid and invalid)
- ✅ In-stock products filtering
- ✅ Creating products (valid and invalid cases)
- ✅ Updating products (success and not found)
- ✅ Updating stock quantity (valid, negative, and not found)
- ✅ Deleting products (success and not found)
- ✅ Integration flow testing
- ✅ All HTTP status codes are properly tested

## Features

1. **Pagination Support** - Efficient data retrieval with page/limit parameters
2. **Advanced Filtering** - Category, brand, price range filters
3. **Search Functionality** - Case-insensitive keyword search
4. **Stock Management** - Track inventory levels
5. **Complete CRUD Operations** - Create, read, update, delete products
6. **Error Handling** - Proper HTTP status codes and error messages
7. **Data Validation** - Input validation on creation and updates
8. **CORS Support** - Enabled for cross-origin requests

## Running the Application

### Build
```bash
mvn clean package -DskipTests
```

### Run
```bash
mvn spring-boot:run
```

### Test
```bash
mvn test
```

## API Usage Examples

### Get all products with pagination
```bash
GET http://localhost:8080/api/products?page=1&limit=5
```

### Search for products
```bash
GET http://localhost:8080/api/products/search?keyword=iPhone
```

### Get products by price range
```bash
GET http://localhost:8080/api/products/price-range?min=100&max=1000
```

### Create a new product
```bash
POST http://localhost:8080/api/products
Content-Type: application/json

{
  "name": "Samsung S25",
  "description": "Latest Samsung smartphone",
  "price": 999.99,
  "category": "Electronics",
  "stockQuantity": 55,
  "brand": "Samsung"
}
```

### Update stock
```bash
PATCH http://localhost:8080/api/products/1/stock?quantity=75
```

### Delete a product
```bash
DELETE http://localhost:8080/api/products/1
```

## Technical Stack

- **Framework:** Spring Boot 4.0.2
- **Language:** Java 21
- **Build Tool:** Maven 3.9.12
- **Testing:** JUnit 5, Spring Test with MockMvc
- **API Style:** REST

## Standards Implemented

1. **HTTP Status Codes:**
   - 200 OK - Successful GET/PUT/PATCH
   - 201 CREATED - POST successful
   - 400 BAD REQUEST - Invalid input
   - 404 NOT FOUND - Resource not found

2. **Response Format:**
   - Consistent JSON response structure
   - Error messages with status
   - Metadata in list responses (count, page, limit)

3. **Input Validation:**
   - Required field validation
   - Price non-negativity check
   - Quantity non-negativity check
   - Price range validation (min <= max)
   - Keyword validation for search

## Architecture

- **Controller Layer:** Handles HTTP requests/responses
- **Service Layer:** Business logic and data management
- **Model Layer:** Product entity
- **In-Memory Storage:** HashMap for product storage (can be replaced with DB)

All endpoints properly handle errors and return appropriate HTTP status codes.
