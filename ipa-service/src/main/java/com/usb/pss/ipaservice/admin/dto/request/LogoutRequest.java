package com.usb.pss.ipaservice.admin.dto.request;

import java.util.UUID;

public record LogoutRequest(
        UUID token
) {
}
