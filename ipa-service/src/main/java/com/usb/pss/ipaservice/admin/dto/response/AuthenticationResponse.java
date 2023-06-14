package com.usb.pss.ipaservice.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class AuthenticationResponse {
    private String accessToken;
    private UUID refreshToken;
}
