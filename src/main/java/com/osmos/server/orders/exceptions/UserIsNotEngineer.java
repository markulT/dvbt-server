package com.osmos.server.orders.exceptions;

public class UserIsNotEngineer extends RuntimeException {
    public UserIsNotEngineer(String msg, Throwable cause) {super(msg, cause);}
    public UserIsNotEngineer(String msg) {super(msg);}
}
