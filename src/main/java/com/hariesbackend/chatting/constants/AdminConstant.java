package com.hariesbackend.chatting.constants;

public enum AdminConstant {
    ADMIN("admin"),
    ADMIN_VIEWER("adminViewer"),
    USER("user"),
    VIEWER("viewer");

    private final String value;

    AdminConstant(String value) {
        this.value = value;
    }

    public String get() {
        return value;
    }
}
