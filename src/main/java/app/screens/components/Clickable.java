package app.screens.components;

import app.App;
import app.graphics.Rectangle;

public interface Clickable {

    boolean handleLeftMouseClick(App app, Rectangle mouseRectangle, boolean doubleClick);
    boolean handleRightMouseClick(App app, Rectangle mouseRectangle);
}
