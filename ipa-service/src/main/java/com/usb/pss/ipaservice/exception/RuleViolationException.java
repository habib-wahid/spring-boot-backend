package com.usb.pss.ipaservice.exception;

import com.usb.pss.ipaservice.common.ExceptionConstant;

public class RuleViolationException extends ApplicationException {

    public RuleViolationException(ExceptionConstant ex) {
        super(ex.name(), ex.getMessage());
    }

}
