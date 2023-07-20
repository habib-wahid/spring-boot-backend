package com.usb.pss.ipaservice.admin.dto.request;

import jakarta.validation.constraints.NotEmpty;
import org.jetbrains.annotations.NotNull;

public record UpdateDepartmentRequest(
    @NotNull
    Long id,
    @NotEmpty
    String name
) {
}
