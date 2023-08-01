package com.usb.pss.ipaservice.admin.dto.response;

import com.usb.pss.ipaservice.admin.model.enums.LoginStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Builder
public class AuthenticationResponse {
    private Long userId;
    private String username;
    private LoginStatus status;
    private String accessToken;
    private UUID refreshToken;
    private Integer otpValidity;
    private List<ModuleActionResponse> modules;
}
