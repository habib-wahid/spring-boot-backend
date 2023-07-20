package com.usb.pss.ipaservice.admin.dto.request;

import jakarta.validation.constraints.NotEmpty;
import org.jetbrains.annotations.NotNull;

public record UpdateUserInfoRequest(
    @NotNull
    Long id,
    @NotEmpty
    String firstName,
    @NotEmpty
    String lastName,
    @NotEmpty
    String emailOfficial,
    String emailOptional,
    @NotNull
    Long departmentId,
    @NotNull
    Long designationId,
    String mobileNumber,
    String telephoneNumber,
    String pointOfSales,
    String accessLevel
) {
}
