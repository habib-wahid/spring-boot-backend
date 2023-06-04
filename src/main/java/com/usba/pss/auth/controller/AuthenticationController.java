package com.usba.pss.auth.controller;

import com.usba.pss.auth.dto.AuthenticationRequest;
import com.usba.pss.auth.dto.AuthenticationResponse;
import com.usba.pss.auth.dto.LogoutRequest;
import com.usba.pss.auth.dto.RegisterRequest;
import com.usba.pss.auth.service.AuthenticationService;
import com.usba.pss.auth.service.LogoutService;
import com.usba.pss.utils.GenericResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final AuthenticationService service;
    private final LogoutService logoutService;

    @PostMapping("/register")
//    public ResponseEntity<AuthenticationResponse> register(
    public GenericResponse register(
            @RequestBody RegisterRequest request
    ) {
        return service.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request,
                       HttpServletResponse response, @RequestBody LogoutRequest logoutRequest) throws IOException {
        logoutService.logoutCustom(request, response, logoutRequest);
    }

}
