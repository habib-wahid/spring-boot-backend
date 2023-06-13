package com.usb.pss.ipaservice.admin.dto.request;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AuthenticationRequest {

    private String username;
    private String password;
}
