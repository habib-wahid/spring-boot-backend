package com.usb.pss.ipaservice.admin.dto.request;

import com.usb.pss.ipaservice.common.validation.ValidPassword;
import jakarta.validation.constraints.NotBlank;

public record ForceChangePasswordRequest(
    @NotBlank
    String username,
    @NotBlank
    String currentPassword,
    @NotBlank @ValidPassword
    String newPassword,
    @NotBlank
    String confirmPassword
) {
}
