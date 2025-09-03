package app.repository;

import app.domain.Product;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static app.domain.MeasuringUnit.GRAM;
import static app.domain.MeasuringUnit.PIECE;
import static app.domain.ProductTag.HEALTHY;

public class MockProductsRepository implements ProductsRepository {

    private static final Logger logger = LoggerFactory.getLogger(MockProductsRepository.class);

    private boolean savedToJson = false;

    List<Product> allProducts = List.of(
            new Product("Apple", PIECE, List.of(HEALTHY)),
            new Product("Banana", PIECE, List.of(HEALTHY)),
            new Product("Chicken Breast", GRAM, List.of(HEALTHY)),
            new Product("Rice", GRAM, List.of(HEALTHY))
    );

    @Override
    public void saveProduct(Product product) {
        allProducts.add(product);

        saveToJson(product);
    }

    private void saveToJson(Product product) {
        String fileName = product.getName().toLowerCase();
        Gson gson = new Gson();
        try {
            File directory = new File("config/products/");
            if (!directory.exists() && !directory.mkdirs()) {
                logger.error("Error while saving product to json file - cannot create directory");
                return;
            }
            FileWriter writer = new FileWriter("config/products/" + fileName + ".json");
            gson.toJson(product, writer);
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Product findProductByName(String name) {
        return allProducts.stream()
                .filter(product -> product.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deleteProductByName(String name) {
        allProducts.removeIf(product -> product.getName().equalsIgnoreCase(name));
    }

    @Override
    public List<Product> findAllProducts() {
        if (!savedToJson) {
            allProducts.forEach(this::saveToJson);
            savedToJson = true;
        }
        return allProducts;
    }

}
