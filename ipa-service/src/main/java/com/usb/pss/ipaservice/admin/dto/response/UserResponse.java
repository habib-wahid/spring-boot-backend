package com.usb.pss.ipaservice.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String userName;
    private String group;
    private String email;
    private String pointOfSale;
    private String accessLevels;
    private Boolean status;
}
