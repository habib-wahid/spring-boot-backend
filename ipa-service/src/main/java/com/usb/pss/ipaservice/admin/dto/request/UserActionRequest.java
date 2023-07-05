package com.usb.pss.ipaservice.admin.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UserActionRequest(

    @NotNull
    Long userId,
    @NotEmpty
    List<Long> actionIds
) {
}
