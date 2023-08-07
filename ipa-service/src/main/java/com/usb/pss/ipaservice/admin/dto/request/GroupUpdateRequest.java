package com.usb.pss.ipaservice.admin.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GroupUpdateRequest(
    @NotNull
    Long id,
    @NotBlank
    String name,
    String description,
    @NotNull
    Boolean active
) {
}
