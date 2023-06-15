package com.usb.pss.ipaservice.exception;

public class ApplicationException extends RuntimeException {
    private final String code;

    public ApplicationException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
