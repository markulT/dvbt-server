package com.osmos.server.orders.exceptions.orderCreationExceptions;

public class OrderCreationException extends RuntimeException {

    public OrderCreationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public OrderCreationException(String msg) {
        super(msg);
    }


}
