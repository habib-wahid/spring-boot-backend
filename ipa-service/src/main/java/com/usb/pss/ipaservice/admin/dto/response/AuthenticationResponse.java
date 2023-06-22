package com.usb.pss.ipaservice.admin.dto.response;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Builder
public class AuthenticationResponse {
    private String accessToken;
    private UUID refreshToken;
    private Set<MenuResponse> menuResponseSet;
}
