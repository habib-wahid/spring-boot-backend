package com.usb.pss.ipaservice.admin.service;

import com.usb.pss.ipaservice.admin.dto.response.AuthenticationResponse;
import com.usb.pss.ipaservice.admin.dto.request.AuthenticationRequest;
import com.usb.pss.ipaservice.admin.dto.response.RefreshAccessTokenResponse;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminUser;
import com.usb.pss.ipaservice.admin.repository.IpaAdminUserRepository;
import com.usb.pss.ipaservice.exception.AuthenticationFailedException;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import static com.usb.pss.ipaservice.common.ExceptionConstants.INVALID_ACCESS_TOKEN;
import static com.usb.pss.ipaservice.common.ExceptionConstants.USER_NOT_EXISTS;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final IpaAdminUserRepository userRepository;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        IpaAdminUser user = userRepository.findUserByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException(
                        USER_NOT_EXISTS, "No user data found with this username...")
                );

        return AuthenticationResponse.builder()
                .accessToken(jwtService.generateAccessToken(user))
                .refreshToken(jwtService.generateRefreshToken(user))
                .build();
    }

    public RefreshAccessTokenResponse refreshAccessToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new AuthenticationFailedException(INVALID_ACCESS_TOKEN, "Token is invalid...");
        }

        authHeader = authHeader.substring(7);
        final String username = jwtService.extractUsername(authHeader);
        IpaAdminUser user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(
                        USER_NOT_EXISTS, "No user data found with this username...")
                );

        if (jwtService.isTokenValid(authHeader, user)) {

            return RefreshAccessTokenResponse.builder()
                    .accessToken(jwtService.generateAccessToken(user))
                    .build();
        }

        throw new AuthenticationFailedException(INVALID_ACCESS_TOKEN, "Token is invalid...");
    }

}
