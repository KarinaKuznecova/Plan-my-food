package app.screens.events;

import app.App;

import java.util.HashMap;

public class CreateNewProductEvent extends ButtonEvent {

    public CreateNewProductEvent(App app) {
        super(app, new HashMap<>());
    }

    @Override
    public void fireEvent() {
        // get parameters from map and create a product from it
    }
}
