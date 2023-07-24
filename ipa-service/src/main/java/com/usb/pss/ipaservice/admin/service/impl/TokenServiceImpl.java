package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.model.entity.RefreshToken;
import com.usb.pss.ipaservice.admin.model.entity.User;
import com.usb.pss.ipaservice.admin.repository.RefreshTokenRepository;
import com.usb.pss.ipaservice.admin.service.iservice.TokenService;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.usb.pss.ipaservice.common.ExceptionConstant.INVALID_ACCESS_TOKEN;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;

    public RefreshToken createNewRefreshToken(User user) {
        return refreshTokenRepository.save(
            RefreshToken.builder()
                .user(user)
                .expiration(LocalDateTime.now().plusMinutes(refreshTokenExpiration))
                .build()
        );

    }

    public RefreshToken getRefreshTokenById(UUID token) {
        return refreshTokenRepository.findByTokenId(token)
            .orElseThrow(() -> new ResourceNotFoundException(INVALID_ACCESS_TOKEN));

    }

    public void deleteRefreshTokenById(UUID token) {
        refreshTokenRepository.deleteById(token);
    }
}
