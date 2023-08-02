package com.usb.pss.ipaservice.admin.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.jetbrains.annotations.NotNull;

public record PersonalInfoRequest(
    @NotBlank
    String firstName,
    String lastName,
    @NotEmpty
    @Email
    String email,
    @NotNull
    Boolean is2faEnabled,
    String mobileNumber,
    String telephoneNumber,
    @NotNull
    Long departmentId,
    @NotNull
    Long designationId
) {
}
