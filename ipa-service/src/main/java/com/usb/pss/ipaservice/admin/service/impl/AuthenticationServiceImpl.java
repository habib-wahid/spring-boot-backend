package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.request.AuthenticationRequest;
import com.usb.pss.ipaservice.admin.dto.request.ForgotPasswordRequest;
import com.usb.pss.ipaservice.admin.dto.request.LogoutRequest;
import com.usb.pss.ipaservice.admin.dto.request.OtpResendRequest;
import com.usb.pss.ipaservice.admin.dto.request.OtpVerifyRequest;
import com.usb.pss.ipaservice.admin.dto.request.ResetPasswordRequest;
import com.usb.pss.ipaservice.admin.dto.response.AuthenticationResponse;
import com.usb.pss.ipaservice.admin.dto.response.OtpResponse;
import com.usb.pss.ipaservice.admin.dto.response.RefreshAccessTokenResponse;
import com.usb.pss.ipaservice.admin.model.entity.Otp;
import com.usb.pss.ipaservice.admin.model.entity.PasswordReset;
import com.usb.pss.ipaservice.admin.model.entity.RefreshToken;
import com.usb.pss.ipaservice.admin.model.entity.User;
import com.usb.pss.ipaservice.admin.repository.PasswordResetRepository;
import com.usb.pss.ipaservice.admin.repository.UserRepository;
import com.usb.pss.ipaservice.admin.service.JwtService;
import com.usb.pss.ipaservice.admin.service.iservice.AuthenticationService;
import com.usb.pss.ipaservice.admin.service.iservice.EmailService;
import com.usb.pss.ipaservice.admin.service.iservice.ModuleService;
import com.usb.pss.ipaservice.admin.service.iservice.OtpService;
import com.usb.pss.ipaservice.admin.service.iservice.TokenService;
import com.usb.pss.ipaservice.admin.service.iservice.UserService;
import com.usb.pss.ipaservice.common.ExceptionConstant;
import com.usb.pss.ipaservice.exception.AuthenticationFailedException;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import com.usb.pss.ipaservice.exception.RuleViolationException;
import com.usb.pss.ipaservice.utils.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import static com.usb.pss.ipaservice.common.ExceptionConstant.INVALID_ACCESS_TOKEN;
import static com.usb.pss.ipaservice.common.ExceptionConstant.INVALID_AUTH_REQUEST;
import static com.usb.pss.ipaservice.common.ExceptionConstant.PASSWORD_NOT_MATCH;
import static com.usb.pss.ipaservice.common.ExceptionConstant.USER_NOT_FOUND_BY_USERNAME;
import static com.usb.pss.ipaservice.common.SecurityConstants.TOKEN_TYPE;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ModuleService moduleService;
    private final TokenService tokenService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final TokenBlackListingService tokenBlackListingService;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetRepository passwordResetRepository;
    private final EmailService emailService;
    private final OtpService otpService;


    @Value("${useExpiringMapToBlackListAccessToken}")
    private boolean useExpiringMapToBlackListAccessToken;

    @Value("${application.reset-password.validity}")
    private Long resetPasswordValidity;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.username(),
                request.password()
            )
        );

        User user = userRepository.findUserAndFetchActionAndPersonalInfoByUsername(request.username())
            .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_BY_USERNAME));

        if (user.is2faEnabled()) {
            Otp otp = otpService.saveAndSend2faOtp(user);
            OtpResponse otpResponse = OtpResponse.builder()
                .username(otp.getUser().getUsername())
                .build();
            return AuthenticationResponse.builder()
                .otpResponse(otpResponse)
                .build();
        } else {
            return generateAuthenticationResponse(user);
        }
    }

    @Override
    public AuthenticationResponse authenticateWithOtp(OtpVerifyRequest request) {
        User user = userService.getUserByUsername(request.username());
        Boolean isValidOtp = otpService.verify2faOtp(user, request);
        if (isValidOtp) {
            return generateAuthenticationResponse(user);
        } else {
            throw new RuleViolationException(ExceptionConstant.INVALID_OTP);
        }
    }

    @Override
    public AuthenticationResponse resend2faOtp(OtpResendRequest request) {
        User user = userRepository.findUserAndFetchActionAndPersonalInfoByUsername(request.username())
            .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_BY_USERNAME));
        if (user.is2faEnabled()) {
            Otp otp = otpService.resend2faOtp(user, request);
            OtpResponse otpResponse = OtpResponse.builder()
                .username(otp.getUser().getUsername())
                .build();
            return AuthenticationResponse.builder()
                .otpResponse(otpResponse)
                .build();
        }
        throw new RuleViolationException(INVALID_AUTH_REQUEST);
    }

    private AuthenticationResponse generateAuthenticationResponse(User user) {
        String accessToken = jwtService.generateAccessToken(user);
        RefreshToken refreshToken = tokenService.createNewRefreshToken(user);

        return AuthenticationResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken.getTokenId())
            .modules(moduleService.getModuleWiseUserMenu(user))
            .build();
    }

    public RefreshAccessTokenResponse refreshAccessToken(UUID token) {

        RefreshToken refreshToken = tokenService.getRefreshTokenById(token);
        if (refreshToken.getExpiration().isBefore(LocalDateTime.now())) {
            throw new AuthenticationFailedException(INVALID_ACCESS_TOKEN);
        }

        return new RefreshAccessTokenResponse(jwtService.generateAccessToken(refreshToken.getUser()));

    }


    public void logout(String authHeader, LogoutRequest request) {
        if (authHeader == null || !authHeader.startsWith(TOKEN_TYPE)) {
            return;
        }
        String accessToken = SecurityUtils.extractTokenFromHeader(authHeader);
        invalidateAccessToken(accessToken);

        tokenService.deleteRefreshTokenById(request.token());
    }

    @Override
    public void sendPasswordResetLink(HttpServletRequest httpServletRequest,
                                      ForgotPasswordRequest forgotPasswordRequest) {
        User user = userRepository
            .findUserByUsername(forgotPasswordRequest.userName())
            .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_BY_USERNAME));
        PasswordReset passwordReset = savePasswordReset(user);
        String siteURL = httpServletRequest.getRequestURL().toString();
        String url = siteURL
            .replace(httpServletRequest.getServletPath(), "") + "/resetPassword?token="
            + passwordReset.getTokenId();
//        try {
//            emailService.sendEmail(user, url);
//        } catch (MessagingException e) {
//            log.error("Email send failure " + e.getMessage());
//            throw new RuleViolationException(ExceptionConstant.EMAIL_NOT_SENT);
//        }
    }

    private PasswordReset savePasswordReset(User user) {
        return passwordResetRepository.save(
            PasswordReset.builder()
                .user(user)
                .expiration(LocalDateTime.now().plusMinutes(resetPasswordValidity))
                .build()
        );
    }

    @Override
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        PasswordReset passwordReset = passwordResetRepository
            .findPasswordResetByTokenId(UUID.fromString(resetPasswordRequest.token()))
            .orElseThrow(() -> new ResourceNotFoundException(ExceptionConstant.RESET_TOKEN_NOT_FOUND));
        if (passwordReset.getExpiration().isBefore(LocalDateTime.now())) {
            throw new RuleViolationException(ExceptionConstant.EMAIL_VALIDITY_EXPIRED);
        }
        User user = passwordReset.getUser();
        if (!resetPasswordRequest.password().equals(resetPasswordRequest.confirmPassword())) {
            throw new ResourceNotFoundException(PASSWORD_NOT_MATCH);
        }
        user.setPassword(passwordEncoder.encode(resetPasswordRequest.password()));
        userRepository.save(user);
        passwordResetRepository.delete(passwordReset);
    }

    public void invalidateAccessToken(String accessToken) {
        if (!useExpiringMapToBlackListAccessToken) {
            Date tokenExpiryDate = jwtService.extractExpiration(accessToken);
            long ttl = getTTLForToken(tokenExpiryDate);
            tokenBlackListingService.blackListTokenWithExpiryTime(accessToken, ttl);
        } else {
            blacklistAccessTokenInExpiringMap(accessToken);
        }
    }


    private void blacklistAccessTokenInExpiringMap(String accessToken) {
        Date tokenExpiryDate = jwtService.extractExpiration(accessToken);
        long ttl = getTTLForToken(tokenExpiryDate);
        tokenBlackListingService.putAccessTokenInExpiringMap(accessToken, ttl);
    }


    private long getTTLForToken(Date date) {
        return Math.max(0, date.toInstant().getEpochSecond() - Instant.now().getEpochSecond());
    }


}
