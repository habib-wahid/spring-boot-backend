package com.usb.pss.ipaservice.admin.service;

import com.usb.pss.ipaservice.admin.dto.LogoutRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.jodah.expiringmap.ExpiringMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final TokenBlackListingService tokenBlackListingService;

    @Value("${useExpiringMapToBlackListAccessToken}")
    private boolean useExpiringMapToBlackListAccessToken;

    private ExpiringMap<String, String> expiringMap = ExpiringMap.builder()
            .variableExpiration()
            .maxSize(1000)
            .build();
    ;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final String accessHeader = request.getHeader("Authorization");
        if (accessHeader == null || !accessHeader.startsWith("Bearer ")) {
            return;
        }

        final String jwtAccess = accessHeader.substring(7);

        var storedToken = tokenRepository.findByRefreshToken(jwtAccess)
                .orElse(null);
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
            SecurityContextHolder.clearContext();
        }
    }

    public void logoutCustom(
            HttpServletRequest request,
            HttpServletResponse response,
            LogoutRequest logoutRequest
    ) {
        invalidateAccessToken(request);
        invalidateRefreshToken(request, logoutRequest);
    }

    public void invalidateAccessToken(HttpServletRequest request) {
        final String accessHeader = request.getHeader("Authorization");
        if (accessHeader == null || !accessHeader.startsWith("Bearer ")) {
            return;
        }

        final String jwtAccess = accessHeader.substring(7);

        if (!useExpiringMapToBlackListAccessToken) {

            Date tokenExpiryDate = jwtService.extractExpiration(jwtAccess);
            long ttl = getTTLForToken(tokenExpiryDate);
            System.out.println("For " + jwtAccess + " ttl seconds: " + ttl);
//            tokenBlackListingService.blackListJwt(jwtAccess);
            tokenBlackListingService.blackListJwtWithExpiryTime(jwtAccess, ttl);
        } else {
            blacklistAccessTokenInExpiringMap(jwtAccess);
        }
    }

    public void invalidateRefreshToken(HttpServletRequest request, LogoutRequest logoutRequest) {

        final String jwtRefresh = logoutRequest.getRefreshToken();

        var storedToken = tokenRepository.findByRefreshToken(jwtRefresh)
                .orElse(null);
        if (storedToken != null) {
            tokenRepository.delete(storedToken);

            SecurityContextHolder.clearContext();
        }
    }

    public boolean checkIfBlacklisted(String token) {

        if (!useExpiringMapToBlackListAccessToken) {
//            String blackListedToken = tokenBlackListingService.getJwtBlackList(token);
            Object blackListedToken = tokenBlackListingService.checkBlackListJwtWithExpiryTime(token);
            if (blackListedToken != null) {
                System.out.println("Tried to access resource with a blacklisted token in redis");
                return true;
            } else {
                return false;
            }
        } else {
            System.out.println(expiringMap.keySet());
            String value = expiringMap.get(token);
            if (expiringMap.containsKey(token)) {
                System.out.println("Tried to access resource with a blacklisted token in expiringmap");
                return true;
            } else {
                return false;
            }
        }
    }

    public void blacklistAccessTokenInExpiringMap(String accessToken) {
        Date tokenExpiryDate = jwtService.extractExpiration(accessToken);
        long ttl = getTTLForToken(tokenExpiryDate);
        System.out.println("For " + accessToken + " ttl: " + ttl);
        expiringMap.put(accessToken, "token", ttl, TimeUnit.SECONDS);
    }

    private long getTTLForToken(Date date) {
        long secondAtExpiry = date.toInstant().getEpochSecond();
        long secondAtLogout = Instant.now().getEpochSecond();
        return Math.max(0, secondAtExpiry - secondAtLogout);
    }
}
