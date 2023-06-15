package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.request.AuthenticationRequest;
import com.usb.pss.ipaservice.admin.dto.request.LogoutRequest;
import com.usb.pss.ipaservice.admin.dto.response.AuthenticationResponse;
import com.usb.pss.ipaservice.admin.dto.response.RefreshAccessTokenResponse;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminRefreshToken;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminUser;
import com.usb.pss.ipaservice.admin.repository.IpaAdminUserRepository;
import com.usb.pss.ipaservice.admin.service.iservice.AuthenticationService;
import com.usb.pss.ipaservice.admin.service.JwtService;
import com.usb.pss.ipaservice.admin.service.iservice.TokenService;
import com.usb.pss.ipaservice.exception.AuthenticationFailedException;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import com.usb.pss.ipaservice.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import net.jodah.expiringmap.ExpiringMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.usb.pss.ipaservice.common.ExceptionConstants.INVALID_ACCESS_TOKEN;
import static com.usb.pss.ipaservice.common.ExceptionConstants.USER_NOT_EXISTS;
import static com.usb.pss.ipaservice.common.SecurityConstants.TOKEN_TYPE;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenService tokenService;
    private final IpaAdminUserRepository userRepository;
    private final TokenBlackListingService tokenBlackListingService;
    @Value("${useExpiringMapToBlackListAccessToken}")
    private boolean useExpiringMapToBlackListAccessToken;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        IpaAdminUser user = userRepository.findUserByUsername(request.username())
                .orElseThrow(() -> new ResourceNotFoundException(
                        USER_NOT_EXISTS, "No user data found with this username...")
                );

        String accessToken = jwtService.generateAccessToken(user);
        System.out.println(accessToken);
        IpaAdminRefreshToken refreshToken = tokenService.createNewRefreshToken(user);

        return new AuthenticationResponse(accessToken, refreshToken.getTokenId());
    }

    public RefreshAccessTokenResponse refreshAccessToken(UUID token) {

        IpaAdminRefreshToken refreshToken = tokenService.getRefreshTokenById(token);
        if (refreshToken.getExpiration().isBefore(LocalDateTime.now())) {
            throw new AuthenticationFailedException(INVALID_ACCESS_TOKEN, "Token expired...");
        }

        return new RefreshAccessTokenResponse(jwtService.generateAccessToken(refreshToken.getUser()));

    }


    public void logout(String authHeader, LogoutRequest request) {
        if (authHeader == null || !authHeader.startsWith(TOKEN_TYPE)) {
            return;
        }
        String accessToken = SecurityUtils.extractTokenFromHeader(authHeader);

        // TO-DO -> Invalidate the access token.
        invalidateAccessToken(accessToken);
        Date expiration = jwtService.extractExpiration(accessToken);

        tokenService.deleteRefreshTokenById(request.token());
    }

    public void invalidateAccessToken(String accessToken) {
        if (!useExpiringMapToBlackListAccessToken) {
            Date tokenExpiryDate = jwtService.extractExpiration(accessToken);
            long ttl = getTTLForToken(tokenExpiryDate);
            System.out.println("For " + accessToken + " ttl seconds: " + ttl);
            tokenBlackListingService.blackListTokenWithExpiryTime(accessToken, ttl);
        } else {
            blacklistAccessTokenInExpiringMap(accessToken);
        }

    }



    public void blacklistAccessTokenInExpiringMap(String accessToken) {
        Date tokenExpiryDate = jwtService.extractExpiration(accessToken);
        long ttl = getTTLForToken(tokenExpiryDate);
        System.out.println("For " + accessToken + " ttl: " + ttl);
        tokenBlackListingService.putAccessTokenInExpiringMap(accessToken,ttl);
    }


    private long getTTLForToken(Date date) {
        return Math.max(0, date.toInstant().getEpochSecond() - Instant.now().getEpochSecond());
    }


}
