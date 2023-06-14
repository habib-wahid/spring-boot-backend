package com.usb.pss.ipaservice.exception;

public class ResourceNotFoundException extends ApplicationException {

    public ResourceNotFoundException(String exceptionCode, String message) {
        super(exceptionCode, message);
    }

}
