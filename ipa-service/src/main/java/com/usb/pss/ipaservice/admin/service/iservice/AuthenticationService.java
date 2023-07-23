package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.request.AuthenticationRequest;
import com.usb.pss.ipaservice.admin.dto.request.ForgotPasswordRequest;
import com.usb.pss.ipaservice.admin.dto.request.LogoutRequest;
import com.usb.pss.ipaservice.admin.dto.request.ResetPasswordRequest;
import com.usb.pss.ipaservice.admin.dto.response.AuthenticationResponse;
import com.usb.pss.ipaservice.admin.dto.response.RefreshAccessTokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.util.UUID;

public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest request);

    RefreshAccessTokenResponse refreshAccessToken(UUID token);

    void logout(String authHeader, LogoutRequest request);

    void updateResetPasswordToken(HttpServletRequest httpServletRequest, ForgotPasswordRequest forgotPasswordRequest);

    void resetPassword(String token, ResetPasswordRequest resetPasswordRequest);

}
