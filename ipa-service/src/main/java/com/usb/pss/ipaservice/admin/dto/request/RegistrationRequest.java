package com.usb.pss.ipaservice.admin.dto.request;

import com.usb.pss.ipaservice.admin.model.enums.AccessLevel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;


public record RegistrationRequest(
    @NotBlank
    String firstName,
    String lastName,
    @NotBlank
    @Email
    String email,
    @NotBlank
    String username,
    @NotBlank
//    @ValidPassword
    String password,
    @NotBlank
    String confirmPassword,
    String mobileNumber,
    String telephoneNumber,
    @NotNull
    Boolean is2faEnabled,
    @NotNull
    Long departmentId,
    @NotNull
    Long designationId,
    @NotBlank
    String userCode,
    @NotNull
    Long userType,
    @NotEmpty
    Set<Long> currencyIds,
    @NotNull
    Long pointOfSaleId,
    @NotNull
    AccessLevel accessLevel,
    @NotBlank
    String airport
) {

}
