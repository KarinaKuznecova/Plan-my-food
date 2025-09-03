package app.repository;

import app.domain.Product;

import java.util.List;

public interface ProductsRepository {

    void saveProduct(Product product);

    Product findProductByName(String name);

    void deleteProductByName(String name);

    List<Product> findAllProducts();
}
