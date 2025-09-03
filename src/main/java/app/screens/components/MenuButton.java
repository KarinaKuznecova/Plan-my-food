package app.screens.components;

import app.App;
import app.graphics.RenderHandler;
import app.graphics.Rectangle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class MenuButton implements Component, Clickable {

    private static final Logger logger = LoggerFactory.getLogger(MenuButton.class);

    private String buttonText;
    private Rectangle rectangle;

    public MenuButton(Rectangle rectangle, String buttonText) {
        this.rectangle = rectangle;
        this.buttonText = buttonText;
    }

    public void update(App app) {

    }

    public void render(App app, RenderHandler renderHandler, Graphics2D graphics2D) {
        renderHandler.drawFrame(graphics2D, rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        int textWidth = renderHandler.getLineLength(graphics2D, buttonText);

        int xCentered = rectangle.getX() + (rectangle.getWidth() / 2) - (textWidth / 2);
        int yCentered = rectangle.getY() + rectangle.getHeight() / 2 + 5;

        renderHandler.renderText(graphics2D, buttonText, xCentered, yCentered);
    }

    public void resize(App app) {

    }

    @Override
    public boolean handleLeftMouseClick(App app, Rectangle mouseRectangle, boolean doubleClick) {
        if (mouseRectangle.intersects(rectangle)) {
            logger.info("Button {} clicked", buttonText);
            switch (buttonText) {
                case "Products" -> app.setCurrentScreen(app.getProductsScreen());
                case "New Product" -> app.setCurrentScreen(app.getNewProductScreen());
                case "Meals" -> app.setCurrentScreen(app.getMealsScreen());
                case "Main" -> app.setCurrentScreen(app.getMainScreen());
                case "Quit" -> System.exit(0);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean handleRightMouseClick(App app, Rectangle mouseRectangle) {
        if (mouseRectangle.intersects(rectangle)) {
            logger.info("Button {} right-clicked", buttonText);
            return true;
        }
        return false;
    }

    @Override
    public Rectangle getRectangle() {
        return rectangle;
    }
}
