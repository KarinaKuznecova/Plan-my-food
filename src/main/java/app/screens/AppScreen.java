package app.screens;

import app.App;
import app.graphics.RenderHandler;

import java.awt.*;

public interface AppScreen {

    void update(App app);
    void render(App app, RenderHandler renderHandler, Graphics2D graphics2D);
    void resize(App app);
    void leftClick(App app, int xScreenRelated, int yScreenRelated, boolean doubleClick);
    void rightClick(App app, int xScreenRelated, int yScreenRelated);
    void mouseOver(App app, Graphics2D graphics2D, int xScreenRelated, int yScreenRelated);
    
    default void keyPressed(App app, int keyCode, char keyChar) {
        // Default empty implementation for backward compatibility
    }
}
