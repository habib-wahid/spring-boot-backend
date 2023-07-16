package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.request.AuthenticationRequest;
import com.usb.pss.ipaservice.admin.dto.request.LogoutRequest;
import com.usb.pss.ipaservice.admin.dto.response.AuthenticationResponse;
import com.usb.pss.ipaservice.admin.dto.response.RefreshAccessTokenResponse;
import com.usb.pss.ipaservice.admin.model.entity.RefreshToken;
import com.usb.pss.ipaservice.admin.model.entity.User;
import com.usb.pss.ipaservice.admin.repository.UserRepository;
import com.usb.pss.ipaservice.admin.service.iservice.AuthenticationService;
import com.usb.pss.ipaservice.admin.service.JwtService;
import com.usb.pss.ipaservice.admin.service.iservice.TokenService;
import com.usb.pss.ipaservice.admin.service.iservice.UserService;
import com.usb.pss.ipaservice.exception.AuthenticationFailedException;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import com.usb.pss.ipaservice.utils.SecurityUtils;

import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import static com.usb.pss.ipaservice.common.ExceptionConstant.INVALID_ACCESS_TOKEN;
import static com.usb.pss.ipaservice.common.ExceptionConstant.USER_NOT_FOUND_BY_USERNAME;
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
//        Set<MenuResponse> menuResponseSet = userService.getAllPermittedMenuByUser(user);

        return AuthenticationResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken.getTokenId())
            .menuResponseSet(new HashSet<>())
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
