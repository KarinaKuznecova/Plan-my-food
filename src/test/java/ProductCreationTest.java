import app.domain.MeasuringUnit;
import app.domain.Product;
import app.domain.ProductTag;
import app.repository.ProductsRepository;
import app.service.ProductService;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Test for product creation functionality
 */
public class ProductCreationTest extends TestCase {

    /**
     * Simple in-memory repository for testing
     */
    private static class TestProductsRepository implements ProductsRepository {
        private final List<Product> products = new ArrayList<>();

        @Override
        public void saveProduct(Product product) {
            products.add(product);
        }

        @Override
        public Product findProductByName(String name) {
            return products.stream()
                    .filter(product -> product.getName().equalsIgnoreCase(name))
                    .findFirst()
                    .orElse(null);
        }

        @Override
        public void deleteProductByName(String name) {
            products.removeIf(product -> product.getName().equalsIgnoreCase(name));
        }

        @Override
        public List<Product> findAllProducts() {
            return new ArrayList<>(products);
        }
    }

    public void testCreateProduct() {
        // Arrange
        TestProductsRepository repository = new TestProductsRepository();
        ProductService productService = new ProductService(repository);
        
        String productName = "Test Product";
        MeasuringUnit unit = MeasuringUnit.GRAM;
        ProductTag tag = ProductTag.DAIRY;
        
        // Act
        Product product = new Product(productName, unit, Arrays.asList(tag));
        productService.createProduct(product);
        
        // Assert
        Product retrievedProduct = productService.getProductByName(productName);
        assertNotNull("Product should be saved and retrievable", retrievedProduct);
        assertEquals("Product name should match", productName, retrievedProduct.getName());
    }
    
    public void testCreateProductWithValidation() {
        // Test that all required fields are properly set
        String productName = "Milk";
        MeasuringUnit unit = MeasuringUnit.MILILITER;
        ProductTag tag = ProductTag.DAIRY;
        
        Product product = new Product(productName, unit, Arrays.asList(tag));
        
        assertNotNull("Product name should not be null", product.getName());
        assertEquals("Product name should match", productName, product.getName());
        assertFalse("Product name should not be empty", product.getName().trim().isEmpty());
    }
    
    public void testCreateMultipleProducts() {
        // Arrange
        TestProductsRepository repository = new TestProductsRepository();
        ProductService productService = new ProductService(repository);
        
        // Act
        Product product1 = new Product("Apple", MeasuringUnit.PIECE, Arrays.asList(ProductTag.HEALTHY));
        Product product2 = new Product("Milk", MeasuringUnit.MILILITER, Arrays.asList(ProductTag.DAIRY));
        
        productService.createProduct(product1);
        productService.createProduct(product2);
        
        // Assert
        List<Product> allProducts = productService.getAllProducts();
        assertEquals("Should have 2 products", 2, allProducts.size());
        
        Product retrievedApple = productService.getProductByName("Apple");
        Product retrievedMilk = productService.getProductByName("Milk");
        
        assertNotNull("Apple should be retrievable", retrievedApple);
        assertNotNull("Milk should be retrievable", retrievedMilk);
    }
}