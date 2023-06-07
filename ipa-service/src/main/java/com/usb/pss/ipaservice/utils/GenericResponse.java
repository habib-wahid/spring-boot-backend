package com.usb.pss.ipaservice.utils;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author HP
 */
@Getter
@Setter
public class GenericResponse implements Serializable {
    private Integer status;
    private String remarks;

    public GenericResponse(Integer status, String remarks) {
        this.status = status;
        this.remarks = remarks;
    }

    public GenericResponse() {
        this.status = ResponseCode.OK.getCode();
    }
}
