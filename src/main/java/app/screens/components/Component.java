package app.screens.components;

import app.App;
import app.graphics.RenderHandler;

import java.awt.*;

public interface Component {

    void update(App app);

    void render(App app, RenderHandler renderHandler, Graphics2D graphics2D);

    void resize(App app);
}
