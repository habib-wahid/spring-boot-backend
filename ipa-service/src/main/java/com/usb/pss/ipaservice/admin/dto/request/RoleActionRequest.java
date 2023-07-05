package com.usb.pss.ipaservice.admin.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

public record RoleActionRequest(
    @NotNull
    Long roleId,
    @NotEmpty
    Set<Long> actionIds
) {
}
