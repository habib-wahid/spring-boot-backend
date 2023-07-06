package com.usb.pss.ipaservice.admin.dto.request;

import jakarta.validation.constraints.NotNull;

public record PasswordPolicyRequest(
    @NotNull
    Integer passwordLength,
    @NotNull
    Boolean containsUppercase,
    @NotNull
    Boolean containsLowercase,
    @NotNull
    Boolean containsDigit,
    @NotNull
    Boolean containsSpecialCharacters
) {
}
