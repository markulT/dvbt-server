package com.osmos.server.schema;

public enum Permission {
    USER_READ("user:read"),
    USER_CREATE("user:create");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
