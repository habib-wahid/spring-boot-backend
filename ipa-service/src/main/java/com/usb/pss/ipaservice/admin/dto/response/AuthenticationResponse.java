package com.usb.pss.ipaservice.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Builder
public class AuthenticationResponse {
    private String accessToken;
    private UUID refreshToken;
    private List<ModuleMenuResponse> modules;
}
