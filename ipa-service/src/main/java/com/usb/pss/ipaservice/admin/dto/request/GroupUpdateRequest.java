package com.usb.pss.ipaservice.admin.dto.request;

import jakarta.validation.constraints.NotBlank;

public record GroupUpdateRequest(
    @NotBlank
    Long id,
    @NotBlank
    String name,
    String description
) {
}
