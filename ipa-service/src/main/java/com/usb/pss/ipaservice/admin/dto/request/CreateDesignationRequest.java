package com.usb.pss.ipaservice.admin.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record CreateDesignationRequest(

    @NotEmpty
    String name
) {
}
