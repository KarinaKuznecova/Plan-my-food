package app.screens.components;

import app.App;
import app.graphics.Rectangle;
import app.graphics.RenderHandler;

import java.awt.*;
import java.util.List;

public class MenuComponent implements Component, Clickable {

    private List<Component> components;
    private MenuButton mainButton;
    private MenuButton mealsButton;
    private MenuButton productsButton;

    private int buttonWidth = 150;

    public MenuComponent(Rectangle rectangle) {
        mainButton = new MenuButton(new Rectangle(rectangle.getX(), rectangle.getY(), buttonWidth, rectangle.getHeight()), "Main");
        mealsButton = new MenuButton(new Rectangle(rectangle.getX() + buttonWidth, rectangle.getY(), buttonWidth, rectangle.getHeight()), "Meals");
        productsButton = new MenuButton(new Rectangle(rectangle.getX() + (buttonWidth * 2), rectangle.getY(), buttonWidth, rectangle.getHeight()), "Products");
        components = List.of(mainButton, mealsButton, productsButton);
    }

    @Override
    public void update(App app) {
        for (Component component : components) {
            component.update(app);
        }
    }

    @Override
    public void render(App app, RenderHandler renderHandler, Graphics2D graphics2D) {
        for (Component component : components) {
            component.render(app, renderHandler, graphics2D);
        }
    }

    public void resize(App app) {

    }

    @Override
    public boolean handleLeftMouseClick(App app, Rectangle mouseRectangle, boolean doubleClick) {
        for (Component component : components) {
            if (component instanceof Clickable clickable) {
                if (clickable.handleLeftMouseClick(app, mouseRectangle, doubleClick)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean handleRightMouseClick(App app, Rectangle mouseRectangle) {
        for (Component component : components) {
            if (component instanceof Clickable clickable) {
                if (clickable.handleRightMouseClick(app, mouseRectangle)) {
                    return true;
                }
            }
        }
        return false;
    }
}
