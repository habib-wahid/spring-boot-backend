package com.usb.pss.ipaservice.admin.controller;

import com.usb.pss.ipaservice.admin.configuration.Person;
import com.usb.pss.ipaservice.admin.dto.request.AuthenticationRequest;
import com.usb.pss.ipaservice.admin.dto.request.ForceChangePasswordRequest;
import com.usb.pss.ipaservice.admin.dto.request.ForgotPasswordRequest;
import com.usb.pss.ipaservice.admin.dto.request.LogoutRequest;
import com.usb.pss.ipaservice.admin.dto.request.OtpResendRequest;
import com.usb.pss.ipaservice.admin.dto.request.OtpVerifyRequest;
import com.usb.pss.ipaservice.admin.dto.request.ResetPasswordRequest;
import com.usb.pss.ipaservice.admin.dto.response.AuthenticationResponse;
import com.usb.pss.ipaservice.admin.dto.response.RefreshAccessTokenResponse;
import com.usb.pss.ipaservice.admin.service.iservice.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.usb.pss.ipaservice.common.constants.APIEndpointConstants.AUTHENTICATION_ENDPOINT;
import static com.usb.pss.ipaservice.common.constants.SecurityConstants.AUTHORIZATION;


@RestController
@RequiredArgsConstructor
@RequestMapping(AUTHENTICATION_ENDPOINT)
@Tag(name = "User Authentication Controller", description = "API Endpoints for authentication related operations.")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final Person person;

    @PostMapping("/login")
    @Operation(summary = "User log-in", parameters = {@Parameter(name = "X-TENANT-ID", description = "Tenant ID Header",
            in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    })
    public AuthenticationResponse authenticate(@RequestBody @Validated AuthenticationRequest request) {
        return authenticationService.authenticate(request);
    }

    //TODO  security = { @SecurityRequirement(name = AUTHORIZATION)  for only development purpose
    @PostMapping("/refreshToken")
    @Operation(
            summary = "Refresh the the access token after token expired",
            security = {@SecurityRequirement(name = AUTHORIZATION)}
    )
    public RefreshAccessTokenResponse refreshAccessToken(@RequestHeader(HttpHeaders.AUTHORIZATION) UUID token) {
        return authenticationService.refreshAccessToken(token);
    }

    @PostMapping("/logout")
    @Operation(summary = "User log-out")
    public void logout(@RequestHeader(AUTHORIZATION) String authHeader,
                       @RequestBody @Validated LogoutRequest logoutRequest) {
        authenticationService.logout(authHeader, logoutRequest);
    }

    @PutMapping("/forceChangePassword")
    @Operation(summary = "Forcefully change expired password.", parameters = {@Parameter(name = "X-TENANT-ID",
            description = "Tenant ID Header",
            in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    })
    public void forceChangePassword(@RequestBody @Validated ForceChangePasswordRequest request) {
        authenticationService.forceChangePassword(request);
    }

    @PostMapping("/forgotPassword")
    @Operation(summary = "User forgot password endpoint", parameters = {@Parameter(name = "X-TENANT-ID",
            description = "Tenant ID Header",
            in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    })
    public void forgotPassword(@RequestBody @Validated ForgotPasswordRequest forgotPasswordRequest,
                               HttpServletRequest request
    ) {
        //TODO need to consider check request header for remote address in case of proxy server or load balancer
        authenticationService.sendPasswordResetLink(forgotPasswordRequest, request.getRemoteAddr());
    }

    @PostMapping("/resetPassword")
    @Operation(summary = "User reset password endpoint with reset token and password reset request body",
            parameters = {@Parameter(name = "X-TENANT-ID", description = "Tenant ID Header",
                    in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
            })
    public void resetPassword(@RequestBody @Validated ResetPasswordRequest resetPasswordRequest) {
        authenticationService.resetPassword(resetPasswordRequest);
    }

    @PostMapping("/verify2faOtp")
    @Operation(summary = "Verify 2FA OTP to complete login for a 2FA enabled user",
            parameters = {@Parameter(name = "X-TENANT-ID", description = "Tenant ID Header",
                    in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
            })
    public AuthenticationResponse authenticateWith2faOtp(@RequestBody @Validated OtpVerifyRequest request) {
        return authenticationService.authenticateWithOtp(request);
    }

    @PostMapping("/resend2faOtp")
    @Operation(summary = "Resend a new 2FA OTP to user's email",
            parameters = {@Parameter(name = "X-TENANT-ID", description = "Tenant ID Header",
                    in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
            })
    public AuthenticationResponse resend2faOtp(@RequestBody @Validated OtpResendRequest request) {
        return authenticationService.resend2faOtp(request);
    }

    @GetMapping("/person-name")
    public String getPersonName() {
        return person.getName();
    }
}
