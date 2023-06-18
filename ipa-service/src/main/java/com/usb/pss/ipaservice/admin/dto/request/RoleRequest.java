package com.usb.pss.ipaservice.admin.dto.request;

import jakarta.validation.constraints.NotBlank;

/**
 * @author Junaid Khan Pathan
 * @date Jun 17, 2023
 */

public record RoleRequest(
        @NotBlank
        String name,
        String description,
        boolean active
) {}
