## Quick Start Guide

### Prerequisites
- Java 21 (or higher)
- Maven 3.9.12 (or compatible)

### Build and Run

#### Step 1: Build the Project
```bash
cd question4-E-Commerce-api
mvn clean package -DskipTests
```

#### Step 2: Run the Application
```bash
mvn spring-boot:run
```

Or run the JAR directly:
```bash
java -jar target/question4-E-Commerce-api-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:8080`

### Test the API

#### Option 1: Using curl

**Get all products:**
```bash
curl http://localhost:8080/api/products
```

**Search for products:**
```bash
curl "http://localhost:8080/api/products/search?keyword=iPhone"
```

**Get products by category:**
```bash
curl http://localhost:8080/api/products/category/Electronics
```

**Get products by price range:**
```bash
curl "http://localhost:8080/api/products/price-range?min=100&max=1000"
```

**Get in-stock products:**
```bash
curl http://localhost:8080/api/products/in-stock
```

**Get specific product:**
```bash
curl http://localhost:8080/api/products/1
```

**Create a new product:**
```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Samsung S25",
    "description": "Latest Samsung smartphone",
    "price": 999.99,
    "category": "Electronics",
    "stockQuantity": 55,
    "brand": "Samsung"
  }'
```

**Update product stock:**
```bash
curl -X PATCH "http://localhost:8080/api/products/1/stock?quantity=75"
```

**Update entire product:**
```bash
curl -X PUT http://localhost:8080/api/products/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "iPhone 15 Pro Max",
    "description": "Updated iPhone description",
    "price": 1099.99,
    "category": "Electronics",
    "stockQuantity": 100,
    "brand": "Apple"
  }'
```

**Delete a product:**
```bash
curl -X DELETE http://localhost:8080/api/products/1
```

#### Option 2: Using Postman or VS Code REST Client

Import the endpoints shown in README.md into your REST client.

#### Option 3: Running Tests

```bash
mvn test
```

This will run all 30+ comprehensive test cases covering:
- CRUD operations
- Pagination
- Search and filtering
- Error cases
- HTTP status codes
- Input validation

### Expected Output

When you run `mvn test`, you should see all tests passing:
```
[INFO] Tests run: 28, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

### API Response Example

**GET /api/products**
```json
{
  "total": 12,
  "page": 1,
  "limit": 10,
  "data": [
    {
      "productId": 1,
      "name": "iPhone 15 Pro",
      "description": "Latest Apple smartphone with A17 Pro chip",
      "price": 999.99,
      "category": "Electronics",
      "stockQuantity": 50,
      "brand": "Apple"
    },
    ...
  ]
}
```

### Troubleshooting

1. **Port 8080 already in use:**
   - Change the port in `application.properties`: `server.port=8081`

2. **Maven not found:**
   - Ensure Maven is in your PATH or use `./mvnw` (Maven Wrapper)

3. **Java version mismatch:**
   - Project requires Java 21. Check: `java -version`

4. **Tests fail to compile:**
   - Run: `mvn clean install` (without -DskipTests)

## Project Structure

```
├── pom.xml                          # Maven configuration
├── README.md                        # Full documentation
├── QUICKSTART.md                    # This file
├── src/
│   ├── main/
│   │   ├── java/question4/question4_E_Commerce_api/
│   │   │   ├── Question4ECommerceApiApplication.java      # Main entry point
│   │   │   ├── controller/
│   │   │   │   └── ProductController.java                 # 11 REST endpoints
│   │   │   ├── model/
│   │   │   │   └── Product.java                          # Product entity
│   │   │   └── service/
│   │   │       └── ProductService.java                   # Business logic
│   │   └── resources/
│   │       └── application.properties                     # Configuration
│   └── test/
│       └── java/question4/question4_E_Commerce_api/
│           └── Question4ECommerceApiApplicationTests.java # 28 test cases
└── target/                         # Build output
```

## Key Features Implemented ✅

- ✅ Product model with 7 fields (productId, name, description, price, category, stockQuantity, brand)
- ✅ 11 REST endpoints covering all requirements
- ✅ Pagination support (page, limit parameters)
- ✅ Search by keyword (case-insensitive)
- ✅ Filter by category and brand
- ✅ Price range filtering
- ✅ In-stock filtering
- ✅ Stock quantity management
- ✅ 12 pre-loaded products across 5 categories
- ✅ 28 comprehensive test cases
- ✅ Proper HTTP status codes (200, 201, 400, 404)
- ✅ Input validation and error handling
- ✅ JSON request/response format
- ✅ CORS support for cross-origin requests

## Contact

For issues or questions, refer to the README.md for detailed API documentation.
