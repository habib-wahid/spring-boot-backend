package com.usb.pss.ipaservice.admin.dto.request;

import com.usb.pss.ipaservice.admin.model.enums.AccessLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;


public record RegistrationRequest(
    @NotNull
    PersonalInfoRequest personalInfoRequest,
    @NotBlank
    String username,
    @NotBlank
//    @ValidPassword
    String password,
    @NotBlank
    String confirmPassword,
    @NotNull
    Boolean is2faEnabled,
    @NotBlank
    String userCode,
    @NotNull
    Long userTypeId,
    @NotEmpty
    Set<Long> currencyIds,
    Set<Long> airportIds,
    @NotNull
    Long pointOfSaleId,
    @NotNull
    AccessLevel accessLevel
) {

}
