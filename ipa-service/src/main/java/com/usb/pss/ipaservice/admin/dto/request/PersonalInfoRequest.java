package com.usb.pss.ipaservice.admin.dto.request;

import jakarta.validation.constraints.NotEmpty;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public record PersonalInfoRequest(
    @NotNull
    @NotEmpty
    String firstName,
    @NotNull
    @NotEmpty
    String lastName,

    @NotNull
    @NotEmpty
    String designation,
    String department,
    @NotNull
    @NotEmpty
    String emailOfficial,
    String emailOther,
    String pointOfSales,
    String airport,
    String accessLevel,
    Set<String> allowedCurrencies
) {
}
