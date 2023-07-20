package com.usb.pss.ipaservice.admin.dto.request;

import com.usb.pss.ipaservice.common.validation.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public record RegistrationRequest(
    @NotBlank
    String firstName,
    String lastName,
    @NotBlank
    @Email
    String email,
    @NotBlank
    String username,
    @NotBlank
    @ValidPassword
    String password,
    @NotBlank
    String confirmPassword,
    Long groupId,
    @NotBlank
    Long departmentId,
    @NotBlank
    Long designationId
) {

}
