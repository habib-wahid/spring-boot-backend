package com.usb.pss.ipaservice.admin.dto.request;


import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record GroupSearchRequest(
    String name,
    String description,
    @NotNull
    Boolean activeStatus,
    Long createdBy,
    LocalDate startCreatedDate,
    LocalDate endCreatedDate
) {
}
