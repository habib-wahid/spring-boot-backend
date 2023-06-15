package com.usb.pss.ipaservice.exception;

import com.usb.pss.ipaservice.common.ExceptionConstant;

public class AuthenticationFailedException extends ApplicationException {

    public AuthenticationFailedException(ExceptionConstant ex) {
        super(ex.name(), ex.getMessage());
    }
}