package com.usb.pss.ipaservice.admin.controller;

import com.usb.pss.ipaservice.admin.dto.request.LogoutRequest;
import com.usb.pss.ipaservice.admin.dto.response.AuthenticationResponse;
import com.usb.pss.ipaservice.admin.dto.request.AuthenticationRequest;
import com.usb.pss.ipaservice.admin.dto.response.RefreshAccessTokenResponse;
import com.usb.pss.ipaservice.admin.service.AuthenticationService;
import com.usb.pss.ipaservice.common.GlobalApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.usb.pss.ipaservice.common.APIEndpointConstants.AUTHENTICATION_ENDPOINT;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTHENTICATION_ENDPOINT)
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public GlobalApiResponse<AuthenticationResponse> authenticate(@RequestBody @Validated AuthenticationRequest request) {
        return new GlobalApiResponse<>(authenticationService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public GlobalApiResponse<RefreshAccessTokenResponse> refreshAccessToken(@RequestHeader(HttpHeaders.AUTHORIZATION) UUID token) {
        return new GlobalApiResponse<>(authenticationService.refreshAccessToken(token));
    }

    @PostMapping("/logout")
    public void logout(String authHeader, @RequestBody @Validated LogoutRequest logoutRequest) {
        authenticationService.logout(authHeader, logoutRequest);
    }
}
