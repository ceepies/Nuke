package duke.modules.events.impl;

public class EventChat extends Event {

    public String message;

    public String getMessage() {
        return message;
    }

    public EventChat(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
