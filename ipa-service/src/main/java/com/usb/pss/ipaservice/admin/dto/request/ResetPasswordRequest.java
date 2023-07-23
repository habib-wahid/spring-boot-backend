package com.usb.pss.ipaservice.admin.dto.request;

import com.usb.pss.ipaservice.common.validation.ValidPassword;
import jakarta.validation.constraints.NotBlank;


public record ResetPasswordRequest(

        @NotBlank
        @ValidPassword
        String password,

        @NotBlank
        @ValidPassword
        String confirmPassword
) {
}
