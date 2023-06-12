package com.usb.pss.ipaservice.exception;

public class BusinessException extends RuntimeException {
    private final String exceptionCode;

    public BusinessException(String exceptionCode, String message) {
        super(message);
        this.exceptionCode = exceptionCode;
    }

    public String getExceptionCode() {
        return exceptionCode;
    }

}
