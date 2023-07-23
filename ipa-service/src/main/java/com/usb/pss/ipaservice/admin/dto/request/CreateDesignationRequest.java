package com.usb.pss.ipaservice.admin.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateDesignationRequest(

    @NotEmpty
    String name,
    @NotNull
    Long departmentId
) {
}
