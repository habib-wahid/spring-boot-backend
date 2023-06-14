package com.usb.pss.ipaservice.admin.dto.response;

import com.usb.pss.ipaservice.utils.GenericResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class AuthenticationResponse extends GenericResponse {
    private String accessToken;
    private UUID refreshToken;
}
