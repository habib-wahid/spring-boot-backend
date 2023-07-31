package com.usb.pss.ipaservice.admin.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ForgotPasswordRequest(

        @NotBlank
        String usernameOrEmail
) {
}
