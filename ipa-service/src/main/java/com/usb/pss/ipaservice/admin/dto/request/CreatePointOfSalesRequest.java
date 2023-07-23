package com.usb.pss.ipaservice.admin.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record CreatePointOfSalesRequest(
    @NotEmpty
    String name
) {
}
