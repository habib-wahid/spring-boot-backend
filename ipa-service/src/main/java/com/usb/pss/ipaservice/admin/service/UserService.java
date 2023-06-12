package com.usb.pss.ipaservice.admin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usb.pss.ipaservice.admin.dto.AuthenticationResponse;
import com.usb.pss.ipaservice.admin.dto.NavigationItemResponse;
import com.usb.pss.ipaservice.admin.dto.UserDto;
import com.usb.pss.ipaservice.admin.dto.request.AuthenticationRequest;
import com.usb.pss.ipaservice.admin.dto.request.RegistrationRequest;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminUser;
import com.usb.pss.ipaservice.admin.repository.IpaAdminMenuRepository;
import com.usb.pss.ipaservice.admin.repository.IpaAdminUserRepository;
import com.usb.pss.ipaservice.exception.BusinessException;
import com.usb.pss.ipaservice.utils.GenericResponse;
import com.usb.pss.ipaservice.utils.ResponseCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.usb.pss.ipaservice.common.ExceptionConstants.PASSWORD_NOT_MATCH;

@Service
@RequiredArgsConstructor
public class UserService {
    private final IpaAdminUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final IpaAdminMenuRepository navigationItemRepository;

    public GenericResponse register(RegistrationRequest request) {
        if (request.getPassword().equals(request.getConfirmPassword())) {
            throw new BusinessException(PASSWORD_NOT_MATCH, "Password does not match...");
        }

        var user = IpaAdminUser.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        repository.save(user);

        return new GenericResponse(HttpStatus.OK, "Successfully Registered");
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            var user = repository.findByUsername(request.getUsername())
                    .orElseThrow();
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);

            String userName = user.getUsername();
            List<NavigationItem> list = navigationItemRepository.getByUserPermission(userName);
            List<NavigationItemResponse> navigationItems = list.stream().map(item -> {
                return NavigationItemResponse.builder()
                        .url(item.getUrl())
                        .key(item.getKey())
                        .label(item.getLabel())
                        .icon(item.getIcon())
                        .build();
            }).toList();

            saveUserToken(user, refreshToken);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .user(new UserDto(
                            user.getId(),
                            user.getFirstName(),
                            user.getLastName(),
                            user.getEmail(),
                            user.getUsername(),
                            ""))
                    .navigationItemList(navigationItems)
                    .build();
        } catch (Exception ex) {
            AuthenticationResponse authenticationResponse = new AuthenticationResponse();
            authenticationResponse.setStatus(ResponseCode.AUTHENTICATION_ERROR.getCode());
            authenticationResponse.setRemarks("Wrong credentials");
            return authenticationResponse;
        }
    }

    private void saveUserToken(IpaAdminUser user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .refreshToken(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(IpaAdminUser user) {
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
                IpaAdminUser user = storedToken.getUser();
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
