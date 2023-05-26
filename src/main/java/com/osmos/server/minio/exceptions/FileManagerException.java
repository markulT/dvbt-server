package com.osmos.server.minio.exceptions;

public class FileManagerException extends RuntimeException {

    public FileManagerException(String msg, Throwable cause) { super(msg, cause); }

    public FileManagerException(String msg) {super(msg);}

}
