package com.devops3.exception;

public class ExistingUserException extends RuntimeException {

    public ExistingUserException(String message) {
        super(message);
    }

}
