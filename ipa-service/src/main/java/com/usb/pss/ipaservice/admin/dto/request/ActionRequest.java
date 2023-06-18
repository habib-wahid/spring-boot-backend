package com.usb.pss.ipaservice.admin.dto.request;

import jakarta.validation.constraints.NotNull;


public record ActionRequest(
        @NotNull
        Long id,
        @NotNull
        boolean active

) {

}
