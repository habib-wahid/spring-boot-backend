package com.usb.pss.ipaservice.admin.dto.response;

import com.usb.pss.ipaservice.utils.GenericResponse;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthenticationResponse extends GenericResponse {
    private String accessToken;
    private String refreshToken;
}
