package com.usb.pss.ipaservice.admin.dto.request;

import com.usb.pss.ipaservice.common.validation.ValidPassword;
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
    @ValidPassword
    String password,
    @NotBlank
    String confirmPassword,
    @NotNull
    Long groupId,
    @NotBlank
    Long departmentId,
    @NotBlank
    Long designationId,
    @NotEmpty
    Set<Long> currencyIds,
    @NotEmpty
    Set<Long> pointOfSaleIds
) {

}
