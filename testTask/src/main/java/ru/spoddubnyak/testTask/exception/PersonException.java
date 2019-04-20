package ru.spoddubnyak.testTask.exception;

public class PersonException extends RuntimeException{
    private final Integer id;

    public PersonException(final String message, final Integer id) {
        super(message);
        this.id = id;
    }
}
