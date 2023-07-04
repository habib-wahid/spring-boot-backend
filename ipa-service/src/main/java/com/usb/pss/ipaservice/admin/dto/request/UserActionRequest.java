package com.usb.pss.ipaservice.admin.dto.request;

import java.util.List;

public record UserActionRequest(

    Long userId,
    List<Long> actionIds
) {
}
