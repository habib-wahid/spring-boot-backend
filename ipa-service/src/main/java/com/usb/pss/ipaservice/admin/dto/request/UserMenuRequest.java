package com.usb.pss.ipaservice.admin.dto.request;

import java.util.List;

public record UserMenuRequest(
    List<Long> menuIds
) {
}
