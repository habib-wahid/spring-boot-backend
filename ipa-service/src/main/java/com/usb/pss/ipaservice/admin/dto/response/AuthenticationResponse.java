package com.usb.pss.ipaservice.admin.dto.response;

import com.usb.pss.ipaservice.utils.GenericResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class AuthenticationResponse extends GenericResponse {
    private String accessToken;
    private UUID refreshToken;
}
