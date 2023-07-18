package com.usb.pss.ipaservice.admin.dto.request;

import jakarta.validation.constraints.NotNull;

public record UserRoleRequest(
    @NotNull
    Long userId,
    @NotNull
    Long roleId
) {
}
