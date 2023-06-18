package com.usb.pss.ipaservice.admin.dto.request;

import com.usb.pss.ipaservice.admin.model.enums.Service;
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
        Service service,
        boolean active
) {}
