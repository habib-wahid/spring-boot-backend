package com.usb.pss.ipaservice.common;

public enum ExceptionConstant {
    PASSWORD_NOT_MATCH("Password and confirm-password does not match."),
    DUPLICATE_USERNAME("User already exists with this username."),
    USER_NOT_FOUND_BY_USERNAME("No user exists with this username."),
    INVALID_ACCESS_TOKEN("Access token is not valid or expired.");

    private final String message;

    ExceptionConstant(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
