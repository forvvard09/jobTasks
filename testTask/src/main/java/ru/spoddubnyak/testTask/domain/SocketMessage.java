package ru.spoddubnyak.testTask.domain;

public class SocketMessage {

    private String message;

    public SocketMessage() {
    }

    public SocketMessage(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}

