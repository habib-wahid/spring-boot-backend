package com.usb.pss.ipaservice.admin.dto.request;

import jakarta.validation.constraints.NotBlank;


public record ResetPasswordRequest(

        @NotBlank
        String password,
        @NotBlank
        String confirmPassword
) {
}
