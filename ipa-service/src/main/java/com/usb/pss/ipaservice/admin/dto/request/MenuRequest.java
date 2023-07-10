package com.usb.pss.ipaservice.admin.dto.request;

import jakarta.validation.constraints.NotBlank;

/**
 * @author Junaid Khan Pathan
 * @date Jun 17, 2023
 */

public record MenuRequest(
    @NotBlank
    String name,
    @NotBlank
    String url,
    String icon,
    long serviceId
) {
}
