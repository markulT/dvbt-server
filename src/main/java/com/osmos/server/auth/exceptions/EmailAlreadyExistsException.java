package com.osmos.server.auth.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
