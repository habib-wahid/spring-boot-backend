package com.usb.pss.ipaservice.admin.dto.request;

import com.usb.pss.ipaservice.common.validation.ValidPassword;
import jakarta.validation.constraints.NotBlank;

public record ChangePassowrdRequest(

    @NotBlank
    String currentPassword,

    @NotBlank
    @ValidPassword
    String newPassword,
    String confirmPassword
) {

}
