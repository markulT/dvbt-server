package com.osmos.server.roles.exceptions;

public class RolenameAlreadyExists extends RuntimeException{
    public RolenameAlreadyExists(String message) {
        super(message);
    }

    public RolenameAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }
}
