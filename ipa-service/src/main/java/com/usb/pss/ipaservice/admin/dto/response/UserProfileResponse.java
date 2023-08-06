package com.usb.pss.ipaservice.admin.dto.response;

import com.usb.pss.ipaservice.inventory.dto.response.AirportResponse;
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
public class UserProfileResponse {
    private Long id;
    private String userName;
    private PersonalInfoResponse personalInfoResponse;
    private Boolean is2faEnabled;
    private String userCode;
    private UserTypeResponse userType;
    private PointOfSaleResponse pointOfSale;
    private List<AccessLevelResponse> accessLevel;
    private List<AirportResponse> airports;
    private List<CurrencyResponse> allowedCurrencies;
}
