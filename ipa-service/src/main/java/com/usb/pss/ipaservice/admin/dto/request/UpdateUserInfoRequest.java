package com.usb.pss.ipaservice.admin.dto.request;

import com.usb.pss.ipaservice.admin.model.entity.Currency;
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
    String emailOfficial,
    String emailOptional,
    @NotNull
    Long departmentId,
    @NotNull
    Long designationId,
    String mobileNumber,
    String telephoneNumber,
    String pointOfSales,
    String accessLevel,
    String airport,
    Set<Long> allowedCurrencies
) {
}
