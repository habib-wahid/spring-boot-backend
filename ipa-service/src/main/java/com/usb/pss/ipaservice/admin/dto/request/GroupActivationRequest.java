package com.usb.pss.ipaservice.admin.dto.request;

import jakarta.validation.constraints.NotNull;

public record GroupActivationRequest(
    @NotNull
    Long groupId,
    @NotNull
    Boolean active
) {
}
