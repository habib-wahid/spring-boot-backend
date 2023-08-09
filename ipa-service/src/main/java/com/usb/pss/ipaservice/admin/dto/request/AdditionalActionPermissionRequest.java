package com.usb.pss.ipaservice.admin.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record AdditionalActionPermissionRequest(

        @NotNull
        Long userId,
        @NotNull
        Long moduleId,
        @NotNull
        Set<Long> actionIds
) {
}
