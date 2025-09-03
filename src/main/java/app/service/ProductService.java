package app.service;

import app.domain.Product;
import app.repository.ProductsRepository;

import java.util.List;

public class ProductService {

    private final ProductsRepository repository;

    public ProductService(ProductsRepository repository) {
        this.repository = repository;
    }

    public void createProduct(Product product) {
        repository.saveProduct(product);
    }

    public Product getProductByName(String name) {
        return repository.findProductByName(name);
    }

    public List<Product> getAllProducts() {
        return repository.findAllProducts();
    }
}
