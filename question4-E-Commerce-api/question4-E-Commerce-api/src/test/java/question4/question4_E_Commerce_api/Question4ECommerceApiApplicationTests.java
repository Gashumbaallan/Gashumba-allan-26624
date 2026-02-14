package question4.question4_E_Commerce_api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import question4.question4_E_Commerce_api.controller.ProductController;
import question4.question4_E_Commerce_api.service.ProductService;

@SpringBootTest
public class Question4ECommerceApiApplicationTests {

	@Autowired
	private ProductController productController;

	@Autowired
	private ProductService productService;

	@Test
	void contextLoads() {
	}

	@Test
	void testProductControllerLoads() {
		org.junit.jupiter.api.Assertions.assertNotNull(productController);
	}

	@Test
	void testProductServiceLoads() {
		org.junit.jupiter.api.Assertions.assertNotNull(productService);
	}

	@Test
	void testProductsInitialized() {
		long count = productService.getTotalProductCount();
		org.junit.jupiter.api.Assertions.assertTrue(count > 0, "Products should be initialized");
		org.junit.jupiter.api.Assertions.assertEquals(12, count, "Should have 12 initial products");
	}

	@Test
	void testGetProductById() {
		var product = productService.getProductById(1L);
		org.junit.jupiter.api.Assertions.assertNotNull(product);
		org.junit.jupiter.api.Assertions.assertEquals("iPhone 15 Pro", product.getName());
	}

	@Test
	void testGetProductsByCategory() {
		var products = productService.getProductsByCategory("Electronics");
		org.junit.jupiter.api.Assertions.assertTrue(products.size() > 0);
	}

	@Test
	void testGetProductsByBrand() {
		var products = productService.getProductsByBrand("Apple");
		org.junit.jupiter.api.Assertions.assertTrue(products.size() > 0);
	}

	@Test
	void testSearchProducts() {
		var products = productService.searchProducts("iPhone");
		org.junit.jupiter.api.Assertions.assertTrue(products.size() > 0);
	}

	@Test
	void testGetProductsByPriceRange() {
		var products = productService.getProductsByPriceRange(100.0, 1000.0);
		org.junit.jupiter.api.Assertions.assertTrue(products.size() > 0);
	}

	@Test
	void testGetProductsInStock() {
		var products = productService.getProductsInStock();
		org.junit.jupiter.api.Assertions.assertTrue(products.size() > 0);
	}

	@Test
	void testCreateProduct() {
		var newProduct = new question4.question4_E_Commerce_api.model.Product(
			null, "Test Product", "Test Description", 99.99, "Test Category", 50, "Test Brand"
		);
		var created = productService.createProduct(newProduct);
		org.junit.jupiter.api.Assertions.assertNotNull(created.getProductId());
	}

	@Test
	void testUpdateProduct() {
		var updated = new question4.question4_E_Commerce_api.model.Product(
			null, "Updated Product", "Updated Description", 199.99, "Electronics", 60, "Apple"
		);
		var result = productService.updateProduct(1L, updated);
		org.junit.jupiter.api.Assertions.assertEquals("Updated Product", result.getName());
	}

	@Test
	void testUpdateStockQuantity() {
		var result = productService.updateStockQuantity(1L, 100);
		org.junit.jupiter.api.Assertions.assertEquals(100, result.getStockQuantity());
	}

	@Test
	void testDeleteProduct() {
		boolean deleted = productService.deleteProduct(12L);
		org.junit.jupiter.api.Assertions.assertTrue(deleted);
	}
}


