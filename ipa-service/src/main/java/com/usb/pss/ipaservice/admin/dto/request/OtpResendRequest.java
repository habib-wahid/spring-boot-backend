package com.usb.pss.ipaservice.admin.dto.request;

import jakarta.validation.constraints.NotNull;

/**
 * @author Junaid Khan Pathan
 * @date Jul 30, 2023
 */

public record OtpResendRequest(
        @NotNull
        String username,
        @NotNull
        String otpIdentifier
) {
}
