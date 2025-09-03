package app.screens;

import app.App;
import app.domain.Product;
import app.graphics.Rectangle;
import app.graphics.RenderHandler;
import app.screens.components.Clickable;
import app.screens.components.Component;
import app.screens.components.MenuComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static app.constants.Colors.productBgColor;
import static app.screens.events.ChangeScreenButtonEvent.EDIT_TAGS_SCREEN;
import static app.screens.events.ChangeScreenButtonEvent.NEW_PRODUCT_SCREEN;

public class ProductsScreen implements AppScreen {

    List<Component> components = new ArrayList<>();

    public ProductsScreen(App app, MenuComponent menuComponent) {
        components.add(menuComponent);
        Rectangle submenuRectangle = new Rectangle(0, menuComponent.getRectangle().getY() + menuComponent.getRectangle().getHeight(), 160, 60);
        Map<String, String> buttonNames = new LinkedHashMap<>();
        buttonNames.put("New Product", NEW_PRODUCT_SCREEN);
        buttonNames.put("Edit Tags", EDIT_TAGS_SCREEN);
        components.add(new MenuComponent(app, submenuRectangle, buttonNames, 160));
    }

    @Override
    public void update(App app) {
        for (Component component : components) {
            component.update(app);
        }
    }

    @Override
    public void render(App app, RenderHandler renderHandler, Graphics2D graphics2D) {
        renderHandler.renderBackground(graphics2D, productBgColor);
        for (Component component : components) {
            component.render(app, renderHandler, graphics2D);
        }

        int startY = 200;
        for (Product product : app.getProductService().getAllProducts()) {
            renderHandler.renderText(graphics2D, product.getName(), 100, startY);
            startY += 40;
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
        app.graphics.Rectangle mouseRectangle = new app.graphics.Rectangle((xScreenRelated), (yScreenRelated), 2, 2);
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
        app.graphics.Rectangle mouseRectangle = new Rectangle((xScreenRelated), (yScreenRelated), 2, 2);
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
