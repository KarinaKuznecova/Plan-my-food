package app.loading;

import app.App;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import static app.constants.Settings.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class LoadingService {

    private final ControllersLoadingService controllersLoadingService;

    public LoadingService() {
        controllersLoadingService = new ControllersLoadingService();
    }

    public void loadUI(App app) {
        app.setDefaultCloseOperation(EXIT_ON_CLOSE);
        if (!FULLSCREEN) {
            app.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
            app.addComponentListener(getComponentAdapter(app));
            app.setResizable(true);
        } else {
            app.setExtendedState(Frame.MAXIMIZED_BOTH);
            app.setUndecorated(true);
            app.setResizable(false);
        }
        app.requestFocus();
        app.setLocationRelativeTo(null);
        app.setVisible(true);
        app.setFocusTraversalKeysEnabled(false);
    }

    private ComponentAdapter getComponentAdapter(App app) {
        return new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent componentEvent) {
                // resize everything here
                if (app.getCurrentScreen() != null) {
                    app.getCurrentScreen().resize(app);
                }
            }
        };
    }

    // getters
    public ControllersLoadingService getControllersLoadingService() {
        return controllersLoadingService;
    }
}
