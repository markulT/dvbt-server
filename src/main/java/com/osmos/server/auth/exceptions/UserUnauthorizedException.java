package com.osmos.server.auth.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserUnauthorizedException extends AuthenticationException {
    public UserUnauthorizedException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UserUnauthorizedException(String msg) {
        super(msg);
    }
}
