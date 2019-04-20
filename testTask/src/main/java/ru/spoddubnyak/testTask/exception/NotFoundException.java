package ru.spoddubnyak.testTask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends PersonException {
    public NotFoundException(Integer id) {
        super("Person " + id + " not found in.", id);
    }
}
