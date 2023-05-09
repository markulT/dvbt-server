package com.osmos.server.towers.exceptions;

public class TowerAlredyExists extends RuntimeException {
    public TowerAlredyExists(String msg) {super(msg);}

    public TowerAlredyExists(String msg, Throwable cause) {super(msg, cause);}
}
