package com.usb.pss.ipaservice.exception;

import com.usb.pss.ipaservice.common.constants.ExceptionConstant;

public class ResourceAlreadyExistsException extends ApplicationException {
    public ResourceAlreadyExistsException(ExceptionConstant ex) {
        super(ex.name(), ex.getMessage());
    }
}
