package com.usb.pss.ipaservice.admin.dto.request;


import java.time.LocalDate;

public record GroupSearchRequest(
    Boolean searchByName,
    String name,
    Boolean searchByDescription,
    String description,
    Boolean searchByActiveStatus,
    Boolean activeStatus,
    Boolean searchByCreatedBy,
    Long createdBy,
    Boolean searchByCreatedDate,
    LocalDate startCreatedDate,
    LocalDate endCreatedDate
) {
}
