package com.example.spring.Config;

public enum UserPermission {
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    COURSE_READ("course:read"),
    COURSE_WRITE("course:write");

    public String getPermission() {
        return permission;
    }

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }
}
