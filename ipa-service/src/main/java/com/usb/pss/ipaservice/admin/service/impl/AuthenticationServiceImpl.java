package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.request.AuthenticationRequest;
import com.usb.pss.ipaservice.admin.dto.request.LogoutRequest;
import com.usb.pss.ipaservice.admin.dto.request.ResetPasswordRequest;
import com.usb.pss.ipaservice.admin.dto.response.AuthenticationResponse;
import com.usb.pss.ipaservice.admin.dto.response.MenuResponse;
import com.usb.pss.ipaservice.admin.dto.response.RefreshAccessTokenResponse;
import com.usb.pss.ipaservice.admin.model.entity.RefreshToken;
import com.usb.pss.ipaservice.admin.model.entity.User;
import com.usb.pss.ipaservice.admin.repository.UserRepository;
import com.usb.pss.ipaservice.admin.service.iservice.AuthenticationService;
import com.usb.pss.ipaservice.admin.service.JwtService;
import com.usb.pss.ipaservice.admin.service.iservice.TokenService;
import com.usb.pss.ipaservice.admin.service.iservice.UserService;
import com.usb.pss.ipaservice.common.ExceptionConstant;
import com.usb.pss.ipaservice.exception.AuthenticationFailedException;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import com.usb.pss.ipaservice.exception.RuleViolationException;
import com.usb.pss.ipaservice.utils.ResetTokenUtils;
import com.usb.pss.ipaservice.utils.SecurityUtils;

import java.util.Set;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import static com.usb.pss.ipaservice.common.ExceptionConstant.INVALID_ACCESS_TOKEN;
import static com.usb.pss.ipaservice.common.ExceptionConstant.USER_NOT_FOUND_BY_USERNAME;
import static com.usb.pss.ipaservice.common.ExceptionConstant.PASSWORD_NOT_MATCH;
import static com.usb.pss.ipaservice.common.SecurityConstants.TOKEN_TYPE;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final TokenBlackListingService tokenBlackListingService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${useExpiringMapToBlackListAccessToken}")
    private boolean useExpiringMapToBlackListAccessToken;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        User user = userRepository.findUserByUsername(request.username())
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_BY_USERNAME));

        String accessToken = jwtService.generateAccessToken(user);
        RefreshToken refreshToken = tokenService.createNewRefreshToken(user);
        Set<MenuResponse> menuResponseSet = userService.getAllPermittedMenuByUser(user);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getTokenId())
                .menuResponseSet(menuResponseSet)
                .build();

//        return new AuthenticationResponse(accessToken, refreshToken.getTokenId());
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
    public void updateResetPasswordToken(HttpServletRequest httpServletRequest) throws MessagingException {
        String email = httpServletRequest.getParameter("email");
        String token = ResetTokenUtils.resetTokenGenerator();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException(ExceptionConstant.USER_NOT_FOUND_BY_EMAIL));
        user.setResetPasswordToken(token);
        String siteURL = httpServletRequest.getRequestURL().toString();
        String url = siteURL.replace(httpServletRequest.getServletPath(), "") + "/reset_password?token=" + token;
        sendEmail(email, url);
        userRepository.save(user);
    }

    @Override
    public void updatePassword(String token, ResetPasswordRequest resetPasswordRequest) {
        User user = userRepository.findByResetPasswordToken(token).orElseThrow(
                () -> new ResourceNotFoundException(ExceptionConstant.USER_NOT_FOUND_BY_RESET_PASSWORD_TOKEN));
        if (!resetPasswordRequest.password().equals(resetPasswordRequest.confirmPassword())) {
            throw new RuleViolationException(PASSWORD_NOT_MATCH);
        }
        user.setPassword(passwordEncoder.encode(resetPasswordRequest.password()));
        user.setResetPasswordToken(null);
        userRepository.save(user);
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


    public void sendEmail(String recipientEmail, String link)
            throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("habib11wahid@gmail.com");
        helper.setTo(recipientEmail);
        String subject = "Here's the link to reset your password";
        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }

}
