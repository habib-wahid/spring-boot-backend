package com.usb.pss.ipaservice.common;

public enum ExceptionConstant {
    ACTION_NOT_FOUND("Action not found"),
    PASSWORD_NOT_MATCH("Passwords mismatched."),
    DUPLICATE_USERNAME("User already exists with this username."),
    USER_NOT_FOUND_BY_USERNAME("No user exists with this username."),
    USER_NOT_FOUND_BY_EMAIL("No user exists with this email"),
    USER_NOT_FOUND_BY_ID("No user exists with this id."),
    USER_NOT_FOUND_BY_RESET_PASSWORD_TOKEN("No user exists with this reset token."),
    INVALID_ACCESS_TOKEN("Access token is not valid or expired."),
    GROUP_NOT_FOUND("Group not found."),
    DUPLICATE_GROUP_NAME("Group name already exists."),
    DUPLICATE_MENU_NAME("Menu name already exists."),
    DUPLICATE_MENU_URL("Menu URL already exists."),
    MENU_NOT_FOUND("Menu not found."),
    PASSWORD_POLICY_NOT_FOUND("Password Policy not found with this id"),
    CURRENT_PASSWORD_NOT_MATCH("Current Passwords mismatched."),
    NEW_PASSWORD_NOT_MATCH("New Passwords mismatched."),
    EMAIL_NOT_SENT("Email Not Sent"),
    EMAIL_VALIDITY_EXPIRED("Email validity expired"),
    RESET_TOKEN_NOT_FOUND("Reset token not found"),
    DEPARTMENT_NOT_FOUND("Department not found by id"),
    DUPLICATE_DEPARTMENT_NAME("A department already exists with this name"),
    DESIGNATION_NOT_FOUND("Designation not found by id"),
    DUPLICATE_DESIGNATION("Designation Already exists with this name."),
    POINT_OF_SALES_NOT_FOUND("Point of Sales not found by ID"),
    DUPLICATE_POINT_OF_SALES("Point of Sales already exists by this name"),

    CURRENCY_NOT_FOUND_BY_ID("No currency exists with this id."),
    EMAIL_DATA_NOT_FOUND("No email data found."),
    OTP_NOT_FOUND("Not OTP found."),
    WRONG_OTP("Incorrect OTP."),
    EXPIRED_OTP("OTP has been expired."),
    INVALID_OTP("Invalid OTP."),
    INVALID_OTP_STATUS_CODE("Invalid OTP Status Code."),
    INVALID_OTP_TYPE_CODE("Invalid OTP Type Code."),
    INVALID_OTP_RESEND_TIMER("Please, wait before resending an OTP."),
    INVALID_EMAIL_TYPE_CODE("Invalid Email Type Code."),
    INVALID_AUTH_REQUEST("Invalid Authentication Request.");

    private final String message;

    ExceptionConstant(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
