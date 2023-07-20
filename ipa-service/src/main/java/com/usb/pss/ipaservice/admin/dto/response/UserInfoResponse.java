package com.usb.pss.ipaservice.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {
    private String firstName;
    private String lastName;
    private String departmentName;
    private String designationName;
    private String emailOfficial;
    private String emailOther;
    private String mobileNumber;
    private String telephoneNumber;
    private String pointOfSales;
    private String accessLevel;
}
