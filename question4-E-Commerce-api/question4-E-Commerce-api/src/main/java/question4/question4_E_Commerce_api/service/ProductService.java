package question4.question4_E_Commerce_api.service;

import question4.question4_E_Commerce_api.model.Product;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private Map<Long, Product> products = new HashMap<>();
    private Long nextProductId = 1L;

    public ProductService() {
        initializeProducts();
    }

    private void initializeProducts() {
        // Initialize with 10+ products with different categories, brands, and prices
        addProduct(new Product(nextProductId++, "iPhone 15 Pro", "Latest Apple smartphone with A17 Pro chip", 999.99, "Electronics", 50, "Apple"));
        addProduct(new Product(nextProductId++, "Samsung Galaxy S24", "Premium Android smartphone", 899.99, "Electronics", 45, "Samsung"));
        addProduct(new Product(nextProductId++, "Sony WH-1000XM5", "Noise cancelling wireless headphones", 399.99, "Audio", 80, "Sony"));
        addProduct(new Product(nextProductId++, "iPad Air", "Versatile tablet with M1 chip", 599.99, "Electronics", 35, "Apple"));
        addProduct(new Product(nextProductId++, "Nike Air Max 90", "Classic sports shoes for everyday wear", 129.99, "Footwear", 120, "Nike"));
        addProduct(new Product(nextProductId++, "Adidas Ultraboost 22", "Premium running shoes with boost technology", 189.99, "Footwear", 95, "Adidas"));
        addProduct(new Product(nextProductId++, "Canon EOS R5", "Professional mirrorless camera", 3899.99, "Photography", 15, "Canon"));
        addProduct(new Product(nextProductId++, "Sony A7R V", "High resolution full-frame camera", 3198.99, "Photography", 20, "Sony"));
        addProduct(new Product(nextProductId++, "Samsung 65 inch QLED TV", "4K QLED television with smart features", 1299.99, "Home Appliances", 25, "Samsung"));
        addProduct(new Product(nextProductId++, "LG Refrigerator", "Smart refrigerator with touch screen", 1799.99, "Home Appliances", 10, "LG"));
        addProduct(new Product(nextProductId++, "Dell XPS 13", "Ultrabook laptop for professionals", 999.99, "Computers", 30, "Dell"));
        addProduct(new Product(nextProductId++, "MacBook Pro 16", "Powerful laptop for developers and creators", 2499.99, "Computers", 20, "Apple"));
    }

    // Add a product to storage
    public void addProduct(Product product) {
        products.put(product.getProductId(), product);
    }

    // Get all products with pagination
    public List<Product> getAllProducts(int page, int limit) {
        List<Product> allProducts = new ArrayList<>(products.values());
        int start = (page - 1) * limit;
        int end = Math.min(start + limit, allProducts.size());
        
        if (start >= allProducts.size()) {
            return new ArrayList<>();
        }
        
        return allProducts.subList(start, end);
    }

    // Get all products without pagination
    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    // Get product by ID
    public Product getProductById(Long productId) {
        return products.get(productId);
    }

    // Get products by category
    public List<Product> getProductsByCategory(String category) {
        return products.values().stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    // Get products by brand
    public List<Product> getProductsByBrand(String brand) {
        return products.values().stream()
                .filter(p -> p.getBrand().equalsIgnoreCase(brand))
                .collect(Collectors.toList());
    }

    // Search products by keyword in name or description
    public List<Product> searchProducts(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return products.values().stream()
                .filter(p -> p.getName().toLowerCase().contains(lowerKeyword) || 
                           p.getDescription().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }

    // Get products within price range
    public List<Product> getProductsByPriceRange(Double minPrice, Double maxPrice) {
        return products.values().stream()
                .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    // Get products in stock (stockQuantity > 0)
    public List<Product> getProductsInStock() {
        return products.values().stream()
                .filter(p -> p.getStockQuantity() > 0)
                .collect(Collectors.toList());
    }

    // Create new product
    public Product createProduct(Product product) {
        if (product.getProductId() == null) {
            product.setProductId(nextProductId++);
        }
        products.put(product.getProductId(), product);
        return product;
    }

    // Update product
    public Product updateProduct(Long productId, Product productDetails) {
        if (products.containsKey(productId)) {
            Product existingProduct = products.get(productId);
            existingProduct.setName(productDetails.getName());
            existingProduct.setDescription(productDetails.getDescription());
            existingProduct.setPrice(productDetails.getPrice());
            existingProduct.setCategory(productDetails.getCategory());
            existingProduct.setStockQuantity(productDetails.getStockQuantity());
            existingProduct.setBrand(productDetails.getBrand());
            return existingProduct;
        }
        return null;
    }

    // Update stock quantity
    public Product updateStockQuantity(Long productId, int quantity) {
        if (products.containsKey(productId)) {
            Product product = products.get(productId);
            product.setStockQuantity(quantity);
            return product;
        }
        return null;
    }

    // Delete product
    public boolean deleteProduct(Long productId) {
        return products.remove(productId) != null;
    }

    // Check if product exists
    public boolean productExists(Long productId) {
        return products.containsKey(productId);
    }

    // Get total count of products
    public long getTotalProductCount() {
        return products.size();
    }
}
