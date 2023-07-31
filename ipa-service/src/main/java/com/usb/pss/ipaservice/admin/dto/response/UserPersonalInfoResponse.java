package com.usb.pss.ipaservice.admin.dto.response;

import com.usb.pss.ipaservice.admin.model.enums.AccessLevel;
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
    private String userName;
    private String firstName;
    private String lastName;
    private DepartmentResponse department;
    private DesignationResponse designation;
    private Boolean is2faEnabled;
    private String userCode;
    private String emailOfficial;
    private String emailOther;
    private String mobileNumber;
    private String telephoneNumber;
    private List<PointOfSaleResponse> pointOfSales;
    private AccessLevel accessLevel;
    private String airport;
    private List<CurrencyResponse> allowedCurrencies;
}
