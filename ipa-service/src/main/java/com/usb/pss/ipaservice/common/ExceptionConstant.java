package com.usb.pss.ipaservice.common;

public enum ExceptionConstant {
    ACTION_NOT_FOUND("Action not found"),
    PASSWORD_NOT_MATCH("Passwords mismatched."),
    DUPLICATE_USERNAME("User already exists with this username."),
    USER_NOT_FOUND_BY_USERNAME("No user exists with this username."),
    USER_NOT_FOUND_BY_ID("No user exists with this id."),
    INVALID_ACCESS_TOKEN("Access token is not valid or expired."),
    GROUP_NOT_FOUND("Group not found."),
    DUPLICATE_GROUP_NAME("Group name already exists."),
    DUPLICATE_MENU_NAME("Menu name already exists."),
    DUPLICATE_MENU_URL("Menu URL already exists."),
    MENU_NOT_FOUND("Menu not found."),
    PASSWORD_POLICY_NOT_FOUND("Password Policy not found with this id"),
    CURRENT_PASSWORD_NOT_MATCH("Current Passwords mismatched."),
    NEW_PASSWORD_NOT_MATCH("New Passwords mismatched."),

    DEPARTMENT_NOT_FOUND("Department not found by id"),
    DUPLICATE_DEPARTMENT_NAME("A department already exists with this name"),
    DESIGNATION_NOT_FOUND("Designation not found by id"),
    DUPLICATE_DESIGNATION("Designation Already exists with this name."),
    POINT_OF_SALES_NOT_FOUND("Point of Sales not found by ID"),
    DUPLICATE_POINT_OF_SALES("Point of Sales already exists by this name"),

    CURRENCY_NOT_FOUND_BY_ID("No currency exists with this id.");

    private final String message;

    ExceptionConstant(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
