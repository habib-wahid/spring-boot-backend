package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.request.AuthenticationRequest;
import com.usb.pss.ipaservice.admin.dto.request.ForceChangePasswordRequest;
import com.usb.pss.ipaservice.admin.dto.request.ForgotPasswordRequest;
import com.usb.pss.ipaservice.admin.dto.request.LogoutRequest;
import com.usb.pss.ipaservice.admin.dto.request.OtpResendRequest;
import com.usb.pss.ipaservice.admin.dto.request.OtpVerifyRequest;
import com.usb.pss.ipaservice.admin.dto.request.ResetPasswordRequest;
import com.usb.pss.ipaservice.admin.dto.response.AuthenticationResponse;
import com.usb.pss.ipaservice.admin.dto.response.RefreshAccessTokenResponse;
import java.util.UUID;

public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest request);

    AuthenticationResponse authenticateWithOtp(OtpVerifyRequest request);

    AuthenticationResponse resend2faOtp(OtpResendRequest request);

    RefreshAccessTokenResponse refreshAccessToken(UUID token);

    void logout(String authHeader, LogoutRequest request);

    void sendPasswordResetLink(ForgotPasswordRequest forgotPasswordRequest, String remoteAddress);

    void resetPassword(ResetPasswordRequest resetPasswordRequest);

    void forceChangePassword(ForceChangePasswordRequest request);
}
