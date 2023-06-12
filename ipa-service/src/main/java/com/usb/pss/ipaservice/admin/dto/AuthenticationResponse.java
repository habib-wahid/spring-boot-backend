package com.usb.pss.ipaservice.admin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.usb.pss.ipaservice.utils.GenericResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse extends GenericResponse {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    private UserDto user;
    private List<NavigationItemResponse> navigationItemList;
}
