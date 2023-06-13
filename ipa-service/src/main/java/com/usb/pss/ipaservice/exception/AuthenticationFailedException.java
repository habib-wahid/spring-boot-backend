package com.usb.pss.ipaservice.exception;

public class AuthenticationFailedException extends ApplicationException{

    public AuthenticationFailedException(String exceptionCode, String message) {
        super(exceptionCode, message);
    }
}
