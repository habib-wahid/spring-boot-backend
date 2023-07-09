package com.usb.pss.ipaservice.admin.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record UserRoleActionRequest(
        @NotNull
        Long userId,
        @NotNull
        Set<Long> roleIds
) {
}
