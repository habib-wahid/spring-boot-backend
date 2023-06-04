package com.usba.pss.auth.service;

import com.usba.pss.auth.dto.AuthenticationRequest;
import com.usba.pss.auth.dto.AuthenticationResponse;
import com.usba.pss.auth.dto.RegisterRequest;
import com.usba.pss.auth.model.token.Token;
import com.usba.pss.auth.repository.TokenRepository;
import com.usba.pss.auth.model.token.TokenType;
import com.usba.pss.auth.model.user.User;
import com.usba.pss.auth.dto.UserDto;
import com.usba.pss.auth.repository.UserRepository;
import com.usba.pss.utils.GenericResponse;
import com.usba.pss.utils.ResponseCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

//    public AuthenticationResponse register(RegisterRequest request) {
    public GenericResponse register(RegisterRequest request) {
        try {
            var user = User.builder()
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(request.getRole())
                    .build();
            var savedUser = repository.save(user);
//            var jwtToken = jwtService.generateToken(user);
//            var refreshToken = jwtService.generateRefreshToken(user);
//    saveUserToken(savedUser, jwtToken);
//            saveUserToken(savedUser, refreshToken);
            return new GenericResponse(ResponseCode.OK.getCode(), "Successfully Registered");
        } catch (Exception ex) {
            return new GenericResponse(ResponseCode.SERVICE_ERROR.getCode(), "Service Error");
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
//        var user = repository.findByEmail(request.getUsername())
            var user = repository.findByUsername(request.getUsername())
                    .orElseThrow();
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
//        revokeAllUserTokens(user); // No need to invalidate all refresh token of a user when he logs in another machine
//    saveUserToken(user, jwtToken);
            saveUserToken(user, refreshToken);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .user(new UserDto(
                            user.getId(),
                            user.getFirstname(),
                            user.getLastname(),
                            user.getEmail(),
                            user.getUsername(),
                            ""))
                    .build();
        } catch (Exception ex) {
            System.out.println(ex);
            AuthenticationResponse authenticationResponse = new AuthenticationResponse();
            authenticationResponse.setStatus(ResponseCode.AUTHENTICATION_ERROR.getCode());
            authenticationResponse.setRemarks("Wrong credentials");
            return authenticationResponse;
        }
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .refreshToken(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        try {
            final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            final String refreshToken;
            final String userEmail;
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return;
            }
            refreshToken = authHeader.substring(7);

//        userEmail = jwtService.extractUsername(refreshToken);
//        if (userEmail != null) {
//            var user = this.repository.findByEmail(userEmail)
//                    .orElseThrow();
//            if (jwtService.isTokenValid(refreshToken, user)) {
//                var accessToken = jwtService.generateToken(user);
//                revokeAllUserTokens(user);
////        saveUserToken(user, accessToken);
//                saveUserToken(user, refreshToken);
//                var authResponse = AuthenticationResponse.builder()
//                        .accessToken(accessToken)
//                        .refreshToken(refreshToken)
//                        .build();
//                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
//            }
//        }

            var storedToken = tokenRepository.findByRefreshToken(refreshToken)
                    .orElse(null);

            if (storedToken != null) {
                User user = storedToken.getUser();
                var accessToken = jwtService.generateToken(user);
                saveUserToken(user, refreshToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        } catch (Exception ex) {

        }
    }
}
