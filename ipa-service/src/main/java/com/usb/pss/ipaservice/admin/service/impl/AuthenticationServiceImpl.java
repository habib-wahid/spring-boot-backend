package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.request.AuthenticationRequest;
import com.usb.pss.ipaservice.admin.dto.request.ForceChangePasswordRequest;
import com.usb.pss.ipaservice.admin.dto.request.ForgotPasswordRequest;
import com.usb.pss.ipaservice.admin.dto.request.LogoutRequest;
import com.usb.pss.ipaservice.admin.dto.request.ResetPasswordRequest;
import com.usb.pss.ipaservice.admin.dto.response.AuthenticationResponse;
import com.usb.pss.ipaservice.admin.dto.response.RefreshAccessTokenResponse;
import com.usb.pss.ipaservice.admin.model.entity.PasswordReset;
import com.usb.pss.ipaservice.admin.model.entity.RefreshToken;
import com.usb.pss.ipaservice.admin.model.entity.User;
import com.usb.pss.ipaservice.admin.repository.PasswordResetRepository;
import com.usb.pss.ipaservice.admin.repository.UserRepository;
import com.usb.pss.ipaservice.admin.service.EmailService;
import com.usb.pss.ipaservice.admin.service.JwtService;
import com.usb.pss.ipaservice.admin.service.iservice.AuthenticationService;
import com.usb.pss.ipaservice.admin.service.iservice.ModuleService;
import com.usb.pss.ipaservice.admin.service.iservice.TokenService;
import com.usb.pss.ipaservice.admin.service.iservice.UserService;
import com.usb.pss.ipaservice.common.constants.ExceptionConstant;
import com.usb.pss.ipaservice.exception.AuthenticationFailedException;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import com.usb.pss.ipaservice.exception.RuleViolationException;
import com.usb.pss.ipaservice.utils.SecurityUtils;
import jakarta.mail.MessagingException;
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

import static com.usb.pss.ipaservice.admin.model.enums.LoginStatus.CHANGE_PASSWORD;
import static com.usb.pss.ipaservice.admin.model.enums.LoginStatus.LOGGED_IN;
import static com.usb.pss.ipaservice.common.constants.ExceptionConstant.INVALID_ACCESS_TOKEN;
import static com.usb.pss.ipaservice.common.constants.ExceptionConstant.PASSWORD_CONFIRM_PASSWORD_NOT_MATCH;
import static com.usb.pss.ipaservice.common.constants.ExceptionConstant.USER_NOT_FOUND_BY_USERNAME;
import static com.usb.pss.ipaservice.common.constants.SecurityConstants.TOKEN_TYPE;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ModuleService moduleService;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final TokenBlackListingService tokenBlackListingService;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetRepository passwordResetRepository;
    private final EmailService emailService;
    private final UserService userService;


    @Value("${useExpiringMapToBlackListAccessToken}")
    private boolean useExpiringMapToBlackListAccessToken;

    @Value("${application.reset-password.validity}")
    private Long resetPasswordValidity;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user = getAuthenticatedUser(request);
        if (
            user.getPasswordExpiryDate() != null &&
                user.getPasswordExpiryDate().isBefore(LocalDateTime.now())
        ) {
            return AuthenticationResponse
                .builder()
                .status(CHANGE_PASSWORD)
                .build();
        }

        String accessToken = jwtService.generateAccessToken(user);
        RefreshToken refreshToken = tokenService.createNewRefreshToken(user);

        return AuthenticationResponse.builder()
            .status(LOGGED_IN)
            .accessToken(accessToken)
            .refreshToken(refreshToken.getTokenId())
            .modules(moduleService.getModuleWiseUserMenu(user))
            .build();
    }

    @Override
    public void forceChangePassword(ForceChangePasswordRequest request) {
        User user = getAuthenticatedUser(
            new AuthenticationRequest(request.username(), request.currentPassword())
        );

        if (!request.newPassword().equals(request.confirmPassword())) {
            throw new RuleViolationException(PASSWORD_CONFIRM_PASSWORD_NOT_MATCH);
        }

        // TO-DO: Need to change the password expiry date based on password policy.
        user.setPasswordExpiryDate(null);
        user.setPassword(passwordEncoder.encode(request.newPassword()));

        userRepository.save(user);
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
        User user = userService.findUserByUsernameOrEmail(forgotPasswordRequest.usernameOrEmail());
        PasswordReset passwordReset = savePasswordReset(user);
        String siteURL = httpServletRequest.getRequestURL().toString();
        String url = siteURL
            .replace(httpServletRequest.getServletPath(), "") + "/resetPassword?token="
            + passwordReset.getTokenId();
        try {
            emailService.sendEmail(user, url);
        } catch (MessagingException e) {
            log.error("Email send failure " + e.getMessage());
            throw new RuleViolationException(ExceptionConstant.EMAIL_NOT_SENT);
        }
    }

    private User getAuthenticatedUser(AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.username(),
                request.password()
            )
        );

        return userRepository.findUserFetchAdditionalActionsByUsername(request.username())
            .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_BY_USERNAME));
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
            throw new ResourceNotFoundException(PASSWORD_CONFIRM_PASSWORD_NOT_MATCH);
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
