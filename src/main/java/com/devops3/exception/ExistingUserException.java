package com.devops3.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ExistingUserException extends RuntimeException {

    public ExistingUserException(String message) {
        super(message);
    }

}
