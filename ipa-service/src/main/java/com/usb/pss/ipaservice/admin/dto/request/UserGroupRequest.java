package com.usb.pss.ipaservice.admin.dto.request;

import jakarta.validation.constraints.NotNull;

public record UserGroupRequest(
    @NotNull
    Long userId,
    @NotNull
    Long groupId
) {
}
