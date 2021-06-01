package com.devops3.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class AccessException extends RuntimeException {

    public AccessException(String message) {
        super(message);
    }

}
