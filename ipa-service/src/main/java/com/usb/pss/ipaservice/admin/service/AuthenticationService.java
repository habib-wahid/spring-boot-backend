package com.usb.pss.ipaservice.admin.service;

import com.usb.pss.ipaservice.admin.dto.request.AuthenticationRequest;
import com.usb.pss.ipaservice.admin.dto.request.LogoutRequest;
import com.usb.pss.ipaservice.admin.dto.response.AuthenticationResponse;
import com.usb.pss.ipaservice.admin.dto.response.RefreshAccessTokenResponse;

import java.util.UUID;

public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest request);

    RefreshAccessTokenResponse refreshAccessToken(UUID token);

    void logout(String authHeader, LogoutRequest request);
}
