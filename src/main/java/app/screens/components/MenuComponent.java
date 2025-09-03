package app.screens.components;

import app.App;
import app.graphics.Rectangle;
import app.graphics.RenderHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MenuComponent implements Component, Clickable {

    private List<Component> components;
    private Rectangle rectangle;

    public MenuComponent(Rectangle rectangle, List<String> buttonNames, int buttonWidth) {
        this.rectangle = rectangle;
        components = new ArrayList<>();

        int buttonCount = 0;
        for (String name : buttonNames) {
            MenuButton button = new MenuButton(new Rectangle(rectangle.getX() + (buttonWidth * buttonCount), rectangle.getY(), buttonWidth, rectangle.getHeight()), name);
            buttonCount++;
            components.add(button);
        }
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

    @Override
    public Rectangle getRectangle() {
        return rectangle;
    }
}
