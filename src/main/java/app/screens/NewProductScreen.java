package app.screens;

import app.App;
import app.graphics.Rectangle;
import app.graphics.RenderHandler;
import app.screens.components.Clickable;
import app.screens.components.Component;
import app.screens.components.MenuButton;
import app.screens.components.MenuComponent;
import app.screens.events.ButtonEvent;
import app.screens.events.CreateNewProductEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static app.constants.Colors.newProductBgColor;

public class NewProductScreen implements AppScreen {

    private final List<Component> components = new ArrayList<>();
    private MenuButton okButton;
    private MenuButton cancelButton;

    public NewProductScreen(App app, MenuComponent menuComponent) {
        ButtonEvent okButtonEvent = new CreateNewProductEvent(app);
        okButton = new MenuButton(new Rectangle(app.getWidth() / 2 - 90, app.getHeight() - 100, 80, 50), "Ok", okButtonEvent);
        components.add(okButton);
        cancelButton = new MenuButton(new Rectangle(app.getWidth() / 2 + 10, app.getHeight() - 100, 80, 50), "Cancel", null);
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
        for (Component component : components) {
            component.render(app, renderHandler, graphics2D);
        }
    }

    @Override
    public void resize(App app) {
        for (Component component : components) {
            component.resize(app);
        }
        okButton.getRectangle().setX(app.getWidth() - 200);
        okButton.getRectangle().setY(app.getHeight() - 100);
        cancelButton.getRectangle().setX(app.getWidth() - 100);
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
}
