package app.screens;

import app.App;
import app.graphics.Rectangle;
import app.graphics.RenderHandler;
import app.screens.components.Clickable;
import app.screens.components.Component;
import app.screens.components.MenuComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static app.constants.Colors.mealsBgColor;
import static app.constants.Colors.newProductBgColor;

public class NewProductScreen implements AppScreen {

    List<Component> components = new ArrayList<>();

    public NewProductScreen(MenuComponent menuComponent) {
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
        for (Component component : components) {
            component.render(app, renderHandler, graphics2D);
        }
    }

    @Override
    public void resize(App app) {
        for (Component component : components) {
            component.resize(app);
        }
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
}
