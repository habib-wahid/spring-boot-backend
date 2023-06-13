package com.usb.pss.ipaservice.exception;

public class ApplicationException extends RuntimeException {
    private final String exceptionCode;

    public ApplicationException(String exceptionCode, String message) {
        super(message);
        this.exceptionCode = exceptionCode;
    }

    public String getExceptionCode() {
        return exceptionCode;
    }
}
