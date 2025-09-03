package app.screens.events;

import app.App;

import java.util.Map;

public class ChangeScreenButtonEvent extends ButtonEvent {

    public static final String TARGET = "target";

    public static final String MAIN_SCREEN = "Main";
    public static final String PRODUCTS_SCREEN = "Products";
    public static final String NEW_PRODUCT_SCREEN = "New Product";
    public static final String EDIT_TAGS_SCREEN = "Edit Tags";
    public static final String MEALS_SCREEN = "Meals";
    public static final String SETTINGS_SCREEN = "Settings";
    public static final String QUIT = "Quit";

    public ChangeScreenButtonEvent(App app, String targetScreen) {
        super(app, Map.of(TARGET, targetScreen));
    }

    @Override
    public void fireEvent() {
        String targetScreen = getEventData().get(TARGET).toString();
        switch (targetScreen) {
            case MAIN_SCREEN -> getApp().setCurrentScreen(getApp().getMainScreen());
            case PRODUCTS_SCREEN -> getApp().setCurrentScreen(getApp().getProductsScreen());
            case NEW_PRODUCT_SCREEN -> getApp().setCurrentScreen(getApp().getNewProductScreen());
            case MEALS_SCREEN -> getApp().setCurrentScreen(getApp().getMealsScreen());
            case QUIT -> System.exit(0);
        }
    }
}
