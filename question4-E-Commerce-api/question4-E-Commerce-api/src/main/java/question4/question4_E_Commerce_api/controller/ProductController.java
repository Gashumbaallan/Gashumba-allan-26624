package question4.question4_E_Commerce_api.controller;

import question4.question4_E_Commerce_api.model.Product;
import question4.question4_E_Commerce_api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * GET /api/products - Get all products with optional pagination
     * Parameters: page (default: 1), limit (default: 10)
     */
    @GetMapping
    public ResponseEntity<?> getAllProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {
        
        List<Product> products;
        if (page > 0 && limit > 0) {
            products = productService.getAllProducts(page, limit);
        } else {
            products = productService.getAllProducts();
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("total", productService.getTotalProductCount());
        response.put("page", page);
        response.put("limit", limit);
        response.put("data", products);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /api/products/{productId} - Get product details by ID
     */
    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable Long productId) {
        Product product = productService.getProductById(productId);
        
        if (product == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Product not found");
            error.put("productId", productId.toString());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    /**
     * GET /api/products/category/{category} - Get products by category
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable String category) {
        List<Product> products = productService.getProductsByCategory(category);
        
        Map<String, Object> response = new HashMap<>();
        response.put("category", category);
        response.put("count", products.size());
        response.put("data", products);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /api/products/brand/{brand} - Get products by brand
     */
    @GetMapping("/brand/{brand}")
    public ResponseEntity<?> getProductsByBrand(@PathVariable String brand) {
        List<Product> products = productService.getProductsByBrand(brand);
        
        Map<String, Object> response = new HashMap<>();
        response.put("brand", brand);
        response.put("count", products.size());
        response.put("data", products);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /api/products/search - Search products by keyword in name or description
     */
    @GetMapping("/search")
    public ResponseEntity<?> searchProducts(@RequestParam String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Search keyword is required");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        
        List<Product> products = productService.searchProducts(keyword);
        
        Map<String, Object> response = new HashMap<>();
        response.put("keyword", keyword);
        response.put("count", products.size());
        response.put("data", products);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /api/products/price-range - Get products within price range
     */
    @GetMapping("/price-range")
    public ResponseEntity<?> getProductsByPriceRange(
            @RequestParam Double min,
            @RequestParam Double max) {
        
        if (min == null || max == null || min < 0 || max < 0 || min > max) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Invalid price range. Min and Max must be positive and Min <= Max");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        
        List<Product> products = productService.getProductsByPriceRange(min, max);
        
        Map<String, Object> response = new HashMap<>();
        response.put("minPrice", min);
        response.put("maxPrice", max);
        response.put("count", products.size());
        response.put("data", products);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * GET /api/products/in-stock - Get products with stockQuantity > 0
     */
    @GetMapping("/in-stock")
    public ResponseEntity<?> getProductsInStock() {
        List<Product> products = productService.getProductsInStock();
        
        Map<String, Object> response = new HashMap<>();
        response.put("count", products.size());
        response.put("data", products);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * POST /api/products - Add new product
     */
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Product name is required");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        
        if (product.getPrice() == null || product.getPrice() < 0) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Product price must be provided and non-negative");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        
        Product createdProduct = productService.createProduct(product);
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Product created successfully");
        response.put("data", createdProduct);
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * PUT /api/products/{productId} - Update product details
     */
    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId, @RequestBody Product productDetails) {
        if (!productService.productExists(productId)) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Product not found");
            error.put("productId", productId.toString());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        
        if (productDetails.getName() == null || productDetails.getName().trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Product name is required");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        
        if (productDetails.getPrice() == null || productDetails.getPrice() < 0) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Product price must be provided and non-negative");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        
        Product updatedProduct = productService.updateProduct(productId, productDetails);
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Product updated successfully");
        response.put("data", updatedProduct);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * PATCH /api/products/{productId}/stock - Update stock quantity
     */
    @PatchMapping("/{productId}/stock")
    public ResponseEntity<?> updateStockQuantity(
            @PathVariable Long productId,
            @RequestParam int quantity) {
        
        if (!productService.productExists(productId)) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Product not found");
            error.put("productId", productId.toString());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        
        if (quantity < 0) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Stock quantity must be non-negative");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        
        Product updatedProduct = productService.updateStockQuantity(productId, quantity);
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Stock quantity updated successfully");
        response.put("productId", productId);
        response.put("newStockQuantity", updatedProduct.getStockQuantity());
        response.put("data", updatedProduct);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * DELETE /api/products/{productId} - Delete product
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        if (!productService.productExists(productId)) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Product not found");
            error.put("productId", productId.toString());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        
        productService.deleteProduct(productId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Product deleted successfully");
        response.put("productId", productId);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
