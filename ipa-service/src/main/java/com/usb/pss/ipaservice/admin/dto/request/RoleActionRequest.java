package com.usb.pss.ipaservice.admin.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record RoleActionRequest(
    @NotNull
    Long roleId,
    @NotEmpty
    List<Long> actionIds
) {
}
