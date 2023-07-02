package com.osmos.server.banner.exceptions;

public class BannerFieldDoesNotExist extends RuntimeException {

    public BannerFieldDoesNotExist(String msg, Throwable cause) {super(msg, cause);}

    public BannerFieldDoesNotExist(String msg) {super(msg);}

}
