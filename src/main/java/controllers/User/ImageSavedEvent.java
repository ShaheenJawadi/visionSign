package controllers.User;

import javafx.event.Event;
import javafx.event.EventType;

public class ImageSavedEvent extends Event {
    public static final EventType<ImageSavedEvent> IMAGE_SAVED = new EventType<>(Event.ANY, "IMAGE_SAVED");

    public ImageSavedEvent() {
        super(IMAGE_SAVED);
    }
}