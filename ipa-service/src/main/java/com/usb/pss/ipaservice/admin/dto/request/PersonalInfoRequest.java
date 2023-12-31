package com.usb.pss.ipaservice.admin.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.jetbrains.annotations.NotNull;

public record PersonalInfoRequest(
    @NotBlank
    String firstName,
    String lastName,
    @NotBlank
    @Email
    String email,
    @Email
    String emailOther,
    String mobileNumber,
    String telephoneNumber,
    @NotNull
    Long departmentId,
    @NotNull
    Long designationId
) {
}
