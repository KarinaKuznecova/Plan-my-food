package app.screens.events;

import app.App;
import app.domain.MeasuringUnit;
import app.domain.Product;
import app.domain.ProductTag;
import app.screens.NewProductScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;

public class CreateNewProductEvent extends ButtonEvent {

    private static final Logger logger = LoggerFactory.getLogger(CreateNewProductEvent.class);
    private final NewProductScreen newProductScreen;

    public CreateNewProductEvent(App app, NewProductScreen newProductScreen) {
        super(app, new HashMap<>());
        this.newProductScreen = newProductScreen;
    }

    @Override
    public void fireEvent() {
        String productName = newProductScreen.getProductName();
        ProductTag selectedTag = newProductScreen.getSelectedTag();
        MeasuringUnit selectedUnit = newProductScreen.getSelectedUnit();
        
        // Validate input
        if (productName == null || productName.trim().isEmpty()) {
            logger.warn("Product name cannot be empty");
            return;
        }
        
        if (selectedTag == null) {
            logger.warn("Product tag must be selected");
            return;
        }
        
        if (selectedUnit == null) {
            logger.warn("Measuring unit must be selected");
            return;
        }
        
        try {
            // Create new product
            Product product = new Product(
                productName.trim(),
                selectedUnit,
                Arrays.asList(selectedTag)
            );
            
            // Save product using the service
            getApp().getProductService().createProduct(product);
            
            logger.info("Product created successfully: {}", productName);
            
            // Clear the form
            newProductScreen.clearForm();
            
            // Navigate back to products screen
            getApp().setCurrentScreen(getApp().getProductsScreen());
            
        } catch (Exception e) {
            logger.error("Error creating product: {}", e.getMessage(), e);
        }
    }
}
