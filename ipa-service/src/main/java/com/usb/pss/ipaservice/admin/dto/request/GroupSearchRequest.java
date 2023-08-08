package com.usb.pss.ipaservice.admin.dto.request;


import java.time.LocalDate;

public record GroupSearchRequest(
    String name,
    String description,
    boolean activeStatus,
    Long createdBy,
    LocalDate startCreatedDate,
    LocalDate endCreatedDate
) {
}
