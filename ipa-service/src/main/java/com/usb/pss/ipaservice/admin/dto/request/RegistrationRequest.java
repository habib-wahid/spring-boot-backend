package com.usb.pss.ipaservice.admin.dto.request;

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
        String password,
        @NotBlank
        String confirmPassword,
        Long groupId
) {

}
