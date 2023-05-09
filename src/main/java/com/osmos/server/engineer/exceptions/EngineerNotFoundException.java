package com.osmos.server.engineer.exceptions;

public class EngineerNotFoundException extends RuntimeException {

    public EngineerNotFoundException(String msg, Throwable cause) {super(msg, cause);}

    public EngineerNotFoundException(String msg) {super(msg);}
}
