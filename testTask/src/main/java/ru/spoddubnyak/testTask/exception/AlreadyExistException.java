package ru.spoddubnyak.testTask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyExistException extends PersonException {

    public AlreadyExistException(final Integer id) {
        super("Error. A person with such " + id + " already exists.", id);
    }
}
