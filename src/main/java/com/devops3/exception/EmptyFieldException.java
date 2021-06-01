package com.devops3.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class EmptyFieldException extends RuntimeException {

    public EmptyFieldException(String message) {
        super(message);
    }
}
