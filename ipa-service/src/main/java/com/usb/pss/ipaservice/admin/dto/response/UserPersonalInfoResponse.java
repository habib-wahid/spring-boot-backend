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
@AllArgsConstructor
@NoArgsConstructor
public class UserPersonalInfoResponse {
    private String firstName;
    private String lastName;
    private DesignationResponse designationResponse;
    private String emailOfficial;
    private String emailOther;
    private String mobileNumber;
    private String telephoneNumber;
    private List<PointOfSaleResponse> pointOfSales;
    private String accessLevel;
    private String airport;
    private List<CurrencyResponse> allowedCurrencies;
}
