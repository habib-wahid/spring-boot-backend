package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.request.*;
import com.usb.pss.ipaservice.admin.dto.response.AuthenticationResponse;
import com.usb.pss.ipaservice.admin.dto.response.RefreshAccessTokenResponse;
import java.util.UUID;

public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest request);

    AuthenticationResponse authenticateWithOtp(OtpVerifyRequest request);
    AuthenticationResponse resend2faOtp(OtpResendRequest request);

    RefreshAccessTokenResponse refreshAccessToken(UUID token);

    void logout(String authHeader, LogoutRequest request);

    void sendPasswordResetLink(ForgotPasswordRequest forgotPasswordRequest);

    void resetPassword(ResetPasswordRequest resetPasswordRequest);

}
