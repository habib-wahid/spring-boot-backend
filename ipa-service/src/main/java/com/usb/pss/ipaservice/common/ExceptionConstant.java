package com.usb.pss.ipaservice.common;

public enum ExceptionConstant {
    ACTION_NOT_FOUND("Action not found"),
    PASSWORD_NOT_MATCH("Password and confirm-password does not match."),
    DUPLICATE_USERNAME("User already exists with this username."),
    USER_NOT_FOUND_BY_USERNAME("No user exists with this username."),
    USER_NOT_FOUND_BY_ID("No user exists with this id."),
    INVALID_ACCESS_TOKEN("Access token is not valid or expired."),
    GROUP_NOT_FOUND("Group not found."),
    DUPLICATE_GROUP_NAME("Group name already exists."),
    DUPLICATE_MENU_NAME("Menu name already exists."),
    DUPLICATE_MENU_URL("Menu URL already exists."),
    MENU_NOT_FOUND("Menu not found."),
    DUPLICATE_ROLE_NAME("Role name already exists."),
    ROLE_NOT_FOUND("Role not found.");

    private final String message;

    ExceptionConstant(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
