package com.usb.pss.ipaservice.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
public class GenericResponse implements Serializable {
    private HttpStatus status;
    private String message;

    public GenericResponse() {
        this.status = HttpStatus.OK;
    }
}
