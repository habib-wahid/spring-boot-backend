/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usba.pss.utils;

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
