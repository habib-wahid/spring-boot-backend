package com.usb.pss.ipaservice.admin.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PasswordPolicyRequest(
    @NotNull
    @Min(value = 4, message = "Password length must not be less than 4")
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
