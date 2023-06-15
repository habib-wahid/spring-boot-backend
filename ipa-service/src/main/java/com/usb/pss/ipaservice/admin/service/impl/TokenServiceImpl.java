package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.model.entity.IpaAdminRefreshToken;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminUser;
import com.usb.pss.ipaservice.admin.repository.IpaAdminRefreshTokenRepository;
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

    private final IpaAdminRefreshTokenRepository refreshTokenRepository;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;

    public IpaAdminRefreshToken createNewRefreshToken(IpaAdminUser user) {
        return refreshTokenRepository.save(
                new IpaAdminRefreshToken(UUID.randomUUID(), user, LocalDateTime.now().plusSeconds(refreshTokenExpiration))
        );

    }

    public IpaAdminRefreshToken getRefreshTokenById(UUID token) {
        return refreshTokenRepository.findByTokenId(token)
                .orElseThrow(() -> new ResourceNotFoundException(INVALID_ACCESS_TOKEN));

    }

    public void deleteRefreshTokenById(UUID token) {
        refreshTokenRepository.deleteById(token);
    }
}
