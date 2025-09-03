package app.loading;

import app.App;
import app.listeners.KeyboardListener;
import app.listeners.MouseEventListener;

import java.awt.*;

public class ControllersLoadingService {

    private KeyboardListener keyboardListener;
    private MouseEventListener mouseEventListener;

    public void loadListeners(App app, Canvas canvas) {
        canvas.setFocusTraversalKeysEnabled(false);     // to allow using keys as Tab

        keyboardListener = new KeyboardListener(app);
        mouseEventListener = new MouseEventListener(app);
        app.addKeyListener(keyboardListener);
        app.addMouseListener(mouseEventListener);
        canvas.addMouseListener(mouseEventListener);
        canvas.addKeyListener(keyboardListener);
        canvas.addMouseWheelListener(mouseEventListener);
    }

    public KeyboardListener getKeyboardListener() {
        return keyboardListener;
    }

    public MouseEventListener getMouseEventListener() {
        return mouseEventListener;
    }
}
