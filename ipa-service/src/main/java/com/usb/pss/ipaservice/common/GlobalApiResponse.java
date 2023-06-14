package com.usb.pss.ipaservice.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class GlobalApiResponse<T> {
    private final HttpStatus status;
    private final String message;
    private final T content;

    public GlobalApiResponse(T content) {
        this.status = HttpStatus.OK;
        this.message = "Successfully Done!";
        this.content = content;
    }
}
