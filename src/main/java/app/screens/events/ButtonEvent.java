package app.screens.events;

import app.App;

import java.util.Map;

public abstract class ButtonEvent {

    private final Map<String, Object> eventData;
    private final App app;

    protected ButtonEvent(App app, Map<String, Object> eventData) {
        this.app = app;
        this.eventData = eventData;
    }

    public Map<String, Object> getEventData() {
        return eventData;
    }

    public App getApp() {
        return app;
    }

    public abstract void fireEvent();
}
