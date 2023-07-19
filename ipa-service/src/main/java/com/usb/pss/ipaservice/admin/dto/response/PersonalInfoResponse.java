package com.usb.pss.ipaservice.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalInfoResponse {
    private String firstName;
    private String lastName;
    private String designation;
    private String department;
    private String emailOfficial;
    private String emailOther;
    private String mobileNumber;
    private String telephoneNumber;
    private String pointOfSales;
    private String accessLevel;
    private String airport;
    private List<String> allowedCurrencies;
}
