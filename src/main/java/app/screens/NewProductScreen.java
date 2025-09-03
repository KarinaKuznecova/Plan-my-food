package app.screens;

import app.App;
import app.domain.MeasuringUnit;
import app.domain.ProductTag;
import app.graphics.Rectangle;
import app.graphics.RenderHandler;
import app.screens.components.Clickable;
import app.screens.components.Component;
import app.screens.components.Dropdown;
import app.screens.components.MenuButton;
import app.screens.components.MenuComponent;
import app.screens.components.TextField;
import app.screens.events.ButtonEvent;
import app.screens.events.ChangeScreenButtonEvent;
import app.screens.events.CreateNewProductEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static app.constants.Colors.newProductBgColor;

public class NewProductScreen implements AppScreen {

    private final List<Component> components = new ArrayList<>();
    private MenuButton okButton;
    private MenuButton cancelButton;
    private TextField productNameField;
    private Dropdown<ProductTag> tagDropdown;
    private Dropdown<MeasuringUnit> unitDropdown;

    public NewProductScreen(App app, MenuComponent menuComponent) {
        int centerX = app.getWidth() / 2;
        int startY = 120;
        
        // Product name text field
        productNameField = new TextField(
            new Rectangle(centerX - 150, startY, 300, 40), 
            "Enter product name..."
        );
        components.add(productNameField);
        
        // Product tag dropdown
        tagDropdown = new Dropdown<>(
            new Rectangle(centerX - 150, startY + 60, 300, 40),
            Arrays.asList(ProductTag.values()),
            "Select a tag..."
        );
        components.add(tagDropdown);
        
        // Measuring unit dropdown
        unitDropdown = new Dropdown<>(
            new Rectangle(centerX - 150, startY + 120, 300, 40),
            Arrays.asList(MeasuringUnit.values()),
            "Select measuring unit..."
        );
        components.add(unitDropdown);
        
        // Buttons
        ButtonEvent okButtonEvent = new CreateNewProductEvent(app, this);
        okButton = new MenuButton(new Rectangle(centerX - 90, app.getHeight() - 100, 80, 50), "Ok", okButtonEvent);
        components.add(okButton);
        
        ButtonEvent cancelButtonEvent = new ChangeScreenButtonEvent(app, ChangeScreenButtonEvent.PRODUCTS_SCREEN);
        cancelButton = new MenuButton(new Rectangle(centerX + 10, app.getHeight() - 100, 80, 50), "Cancel", cancelButtonEvent);
        components.add(cancelButton);

        components.add(menuComponent);
    }

    @Override
    public void update(App app) {
        for (Component component : components) {
            component.update(app);
        }
    }

    @Override
    public void render(App app, RenderHandler renderHandler, Graphics2D graphics2D) {
        renderHandler.renderBackground(graphics2D, newProductBgColor);
        
        // Render title
        graphics2D.setColor(Color.BLACK);
        renderHandler.renderText(graphics2D, "Create New Product", app.getWidth() / 2 - 80, 80);
        
        // Render labels
        renderHandler.renderText(graphics2D, "Product Name:", app.getWidth() / 2 - 150, 115);
        renderHandler.renderText(graphics2D, "Tag:", app.getWidth() / 2 - 150, 175);
        renderHandler.renderText(graphics2D, "Measuring Unit:", app.getWidth() / 2 - 150, 235);
        
        for (Component component : components) {
            component.render(app, renderHandler, graphics2D);
        }
    }

    @Override
    public void resize(App app) {
        for (Component component : components) {
            component.resize(app);
        }
        okButton.getRectangle().setX(app.getWidth() / 2 - 90);
        okButton.getRectangle().setY(app.getHeight() - 100);
        cancelButton.getRectangle().setX(app.getWidth() / 2 + 10);
        cancelButton.getRectangle().setY(app.getHeight() - 100);
    }

    @Override
    public void leftClick(App app, int xScreenRelated, int yScreenRelated, boolean doubleClick) {
        Rectangle mouseRectangle = new Rectangle((xScreenRelated), (yScreenRelated), 2, 2);
        boolean stoppedChecking = false;
        for (Component component : components) {
            if (!stoppedChecking) {
                if (component instanceof Clickable clickable) {
                    stoppedChecking = clickable.handleLeftMouseClick(app, mouseRectangle, doubleClick);
                }
            }
        }
    }

    @Override
    public void rightClick(App app, int xScreenRelated, int yScreenRelated) {
        Rectangle mouseRectangle = new Rectangle((xScreenRelated), (yScreenRelated), 2, 2);
        boolean stoppedChecking = false;
        for (Component component : components) {
            if (!stoppedChecking) {
                if (component instanceof Clickable clickable) {
                    stoppedChecking = clickable.handleLeftMouseClick(app, mouseRectangle, false);
                }
            }
        }
    }

    @Override
    public void mouseOver(App app, Graphics2D graphics2D, int xScreenRelated, int yScreenRelated) {

    }
    
    @Override
    public void keyPressed(App app, int keyCode, char keyChar) {
        // Forward key input to the text field
        productNameField.handleKeyInput(keyChar, keyCode);
    }
    
    // Getters for the form data
    public String getProductName() {
        return productNameField.getText();
    }
    
    public ProductTag getSelectedTag() {
        return tagDropdown.getSelectedOption();
    }
    
    public MeasuringUnit getSelectedUnit() {
        return unitDropdown.getSelectedOption();
    }
    
    public void clearForm() {
        productNameField.setText("");
        tagDropdown.setSelectedOption(null);
        unitDropdown.setSelectedOption(null);
    }
}
