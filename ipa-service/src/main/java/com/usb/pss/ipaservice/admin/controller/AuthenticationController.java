package com.usb.pss.ipaservice.admin.controller;

import static com.usb.pss.ipaservice.common.APIEndpointConstants.AUTHENTICATION_ENDPOINT;
import static com.usb.pss.ipaservice.common.SecurityConstants.AUTHORIZATION;

import com.usb.pss.ipaservice.admin.dto.request.AuthenticationRequest;
import com.usb.pss.ipaservice.admin.dto.request.LogoutRequest;
import com.usb.pss.ipaservice.admin.dto.request.ResetPasswordRequest;
import com.usb.pss.ipaservice.admin.dto.response.AuthenticationResponse;
import com.usb.pss.ipaservice.admin.dto.response.RefreshAccessTokenResponse;
import com.usb.pss.ipaservice.admin.service.iservice.AuthenticationService;

import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping(AUTHENTICATION_ENDPOINT)
@Tag(name = "User Authentication Controller", description = "API Endpoints for authentication related operations.")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @Operation(summary = "User log-in")
    public AuthenticationResponse authenticate(@RequestBody @Validated AuthenticationRequest request) {
        return authenticationService.authenticate(request);
    }

    @PostMapping("/refreshToken")
    @Operation(summary = "Refresh-token")
    public RefreshAccessTokenResponse refreshAccessToken(@RequestHeader(HttpHeaders.AUTHORIZATION) UUID token) {
        return authenticationService.refreshAccessToken(token);
    }

    @PostMapping("/logout")
    @Operation(summary = "User log-out")
    public void logout(@RequestHeader(AUTHORIZATION) String authHeader,
                       @RequestBody @Validated LogoutRequest logoutRequest) {
        authenticationService.logout(authHeader, logoutRequest);
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "User forgot password endpoint")
    public void forgotPassword(HttpServletRequest httpServletRequest) throws MessagingException {
        authenticationService.updateResetPasswordToken(httpServletRequest);
    }

    @PostMapping("/reset-password")
    @Operation(summary = "User reset password endpoint with reset token and password reset request body")
    public void resetPassword(@Param(value = "token") String token,
                              @RequestBody ResetPasswordRequest resetPasswordRequest) {
        authenticationService.updatePassword(token, resetPasswordRequest);
    }
}
