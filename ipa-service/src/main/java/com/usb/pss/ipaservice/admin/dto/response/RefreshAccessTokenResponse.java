package com.usb.pss.ipaservice.admin.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RefreshAccessTokenResponse {
    private String accessToken;
}
