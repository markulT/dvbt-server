package com.osmos.server.auth.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserDoesNotExistException extends AuthenticationException {
    public UserDoesNotExistException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UserDoesNotExistException(String msg) {
        super(msg);
    }
}
