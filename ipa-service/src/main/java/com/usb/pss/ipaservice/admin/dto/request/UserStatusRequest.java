package com.usb.pss.ipaservice.admin.dto.request;

import jakarta.validation.constraints.NotNull;

public record UserStatusRequest(
    @NotNull
    Long userId,
    @NotNull
    Boolean userStatus
) {
}
