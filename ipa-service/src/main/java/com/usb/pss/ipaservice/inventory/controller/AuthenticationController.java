package com.usb.pss.ipaservice.inventory.controller;

import com.usb.pss.ipaservice.inventory.dto.AuthenticationRequest;
import com.usb.pss.ipaservice.inventory.dto.AuthenticationResponse;
import com.usb.pss.ipaservice.inventory.dto.LogoutRequest;
import com.usb.pss.ipaservice.inventory.dto.RegisterRequest;
import com.usb.pss.ipaservice.inventory.service.AuthenticationService;
import com.usb.pss.ipaservice.inventory.service.LogoutService;
import com.usb.pss.ipaservice.utils.GenericResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final LogoutService logoutService;

    @PostMapping("/register")
//    public ResponseEntity<AuthenticationResponse> register(
    public GenericResponse register(
            @RequestBody RegisterRequest request
    ) {
        return authenticationService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request,
                       HttpServletResponse response, @RequestBody LogoutRequest logoutRequest) throws IOException {
        logoutService.logoutCustom(request, response, logoutRequest);
    }

}
