package com.usb.pss.ipaservice.utils;

public enum ResponseCode {
    OK(200),
    PENDING(201),
    GENERAL_ERROR(100),
    DUPLICATE_ENTRY(101),
    UNSPECIFIED_ERROR(102),
    BAD_REQUEST(400),
    AUTHENTICATION_ERROR(401),
    REQUIRED_PARAMETER_MISSING(402),
    SERVICE_NOT_IMPLEMENTED(503),
    SERVICE_ERROR(500),
    DATA_NOT_FOUND(404);

    private Integer code;

    ResponseCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public static ResponseCode getValue(int index) {
        try {
            return values()[index];
        } catch (Exception ex) {
            return GENERAL_ERROR;
        }
    }
}

