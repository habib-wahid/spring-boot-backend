package com.usb.pss.ipaservice.admin.controller;

import com.usb.pss.ipaservice.admin.dto.response.AuthenticationResponse;
import com.usb.pss.ipaservice.admin.dto.request.AuthenticationRequest;
import com.usb.pss.ipaservice.admin.dto.response.RefreshAccessTokenResponse;
import com.usb.pss.ipaservice.admin.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public RefreshAccessTokenResponse refreshAccessToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        return authenticationService.refreshAccessToken(authHeader);
    }

//    @PostMapping("/logout")
//    public void logout(HttpServletRequest request,
//                       HttpServletResponse response, @RequestBody LogoutRequest logoutRequest) throws IOException {
//        logoutService.logoutCustom(request, response, logoutRequest);
//    }
}
