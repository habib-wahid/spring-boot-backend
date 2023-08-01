package com.usb.pss.ipaservice.admin.dto.request;

import jakarta.validation.constraints.NotNull;

/**
 * @author Junaid Khan Pathan
 * @date Jul 18, 2023
 */

public record OtpVerifyRequest(
        @NotNull
        String username,
        @NotNull
        String otpCode
) {
}
