package com.usb.pss.ipaservice.exception;

public class RuleViolationException extends ApplicationException {

    public RuleViolationException(String exceptionCode, String message) {
        super(exceptionCode, message);
    }

}
