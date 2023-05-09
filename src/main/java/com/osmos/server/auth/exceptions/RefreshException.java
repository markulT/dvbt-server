package com.osmos.server.auth.exceptions;

import org.springframework.security.core.AuthenticationException;

public class RefreshException extends AuthenticationException {
    public RefreshException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public RefreshException(String msg) {
        super(msg);
    }
}
