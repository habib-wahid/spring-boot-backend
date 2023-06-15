package com.usb.pss.ipaservice.exception;

import com.usb.pss.ipaservice.common.ExceptionConstant;

public class ResourceNotFoundException extends ApplicationException {

    public ResourceNotFoundException(ExceptionConstant ex) {
        super(ex.name(), ex.getMessage());
    }

}
