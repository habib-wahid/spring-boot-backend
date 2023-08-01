package com.usb.pss.ipaservice.admin.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.jetbrains.annotations.NotNull;


public record UpdateUserInfoRequest(
    @NotNull
    Long id,
    @NotEmpty
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
