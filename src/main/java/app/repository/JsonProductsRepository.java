package app.repository;

import app.domain.Product;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JsonProductsRepository implements ProductsRepository {

    private static final Logger logger = LoggerFactory.getLogger(JsonProductsRepository.class);

    private List<Product> cachedProducts = new ArrayList<>();

    @Override
    public void saveProduct(Product product) {
        cachedProducts.add(product);

        saveToFile(product);
    }

    private void saveToFile(Product product) {
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
        if (cachedProducts.stream().anyMatch(p -> p.getName().equalsIgnoreCase(name))) {
            return cachedProducts.stream().filter(p -> p.getName().equalsIgnoreCase(name)).findFirst().orElse(updateCacheFromFile(name));
        }
        return updateCacheFromFile(name);
    }

    private Product updateCacheFromFile(String name) {
        Product productFromFile = readFromFile(name);
        if (productFromFile != null) {
            cachedProducts.add(productFromFile);
        }
        return productFromFile;
    }

    private Product readFromFile(String name) {
        logger.info("Loading product from save file");

        File directory = new File("config/products/");
        if (directory.listFiles() == null || directory.listFiles().length == 0) {
            logger.info("No product found");
        }
        Gson gson = new Gson();

        try (Reader reader = new FileReader(name + ".json")) {
            return gson.fromJson(reader, Product.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteProductByName(String name) {

    }

    @Override
    public List<Product> findAllProducts() {
        if (!cachedProducts.isEmpty()) {
            return cachedProducts;
        }
        List<Product> productsFromFiles = readProductsFromFiles();
        cachedProducts = productsFromFiles != null ? productsFromFiles : new ArrayList<>();
        return productsFromFiles;
    }

    private List<Product> readProductsFromFiles() {
        logger.info("Loading products saves");

        List<Product> products = new ArrayList<>();
        File directory = new File("config/products/");
        if (directory.listFiles() == null || directory.listFiles().length == 0) {
            logger.info("No products found");
            return null;
        }
        Gson gson = new Gson();
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isDirectory()) {
                continue;
            }
            try (Reader reader = new FileReader(file)) {
                products.add(gson.fromJson(reader, Product.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return products;
    }

}
