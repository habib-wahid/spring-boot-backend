package com.usb.pss.ipaservice.admin.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public record UpdateUserInfoRequest(
    @NotNull
    Long id,
    @NotEmpty
    String firstName,
    @NotEmpty
    String lastName,
    @NotEmpty
    @Email
    String emailOfficial,
    @Email
    String emailOptional,
    @NotNull
    Long departmentId,
    @NotNull
    Long designationId,
    String mobileNumber,
    String telephoneNumber,
    @NotEmpty
    Set<Long> pointOfSaleIds,
    String accessLevel,
    String airport,
    @NotEmpty
    Set<Long> allowedCurrencyIds
) {
}
