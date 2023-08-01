package com.usb.pss.ipaservice.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/*
 * We will not this response generally.
 * If needed, this class needs to be customized.
 * */

@AllArgsConstructor
@Getter
public class CustomApiResponse<T> {
    private final HttpStatus status;
    private final String message;
    private final T content;

    public CustomApiResponse(T content) {
        this.status = HttpStatus.OK;
        this.message = "Successfully Done!";
        this.content = content;
    }
}
