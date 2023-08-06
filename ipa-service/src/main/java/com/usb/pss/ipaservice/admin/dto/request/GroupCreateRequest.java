package com.usb.pss.ipaservice.admin.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author Junaid Khan Pathan
 * @date Jun 17, 2023
 */

public record GroupCreateRequest(
    @NotBlank
    String name,
    String description,
    @NotNull
    Boolean active
) {
}
