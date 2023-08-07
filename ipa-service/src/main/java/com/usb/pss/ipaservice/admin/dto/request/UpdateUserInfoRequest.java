package com.usb.pss.ipaservice.admin.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.jetbrains.annotations.NotNull;


public record UpdateUserInfoRequest(
    @NotNull
    Long id,
    @NotBlank
    @Email
    String emailOfficial,
    @Email
    String emailOther,
    @NotNull
    Boolean is2faEnabled,
    String mobileNumber,
    String telephoneNumber
) {
}
