package com.usb.pss.ipaservice.admin.controller;

import com.usb.pss.ipaservice.admin.dto.request.PasswordPolicyRequest;
import com.usb.pss.ipaservice.admin.service.iservice.PasswordPolicyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static com.usb.pss.ipaservice.common.constants.APIEndpointConstants.PASSWORD_POLICY_ENDPOINT;

@RestController
@RequiredArgsConstructor
@RequestMapping(PASSWORD_POLICY_ENDPOINT)
@Tag(name = "Password Policy Controller", description = "API Endpoints for password policy related operations.")
public class PasswordPolicyController {

    private final PasswordPolicyService passwordPolicyService;

    @PutMapping
    @PreAuthorize("hasAnyAuthority('UPDATE_PASSWORD_POLICY')")
    @Operation(summary = "Update password policy")
    public void updatePasswordPolicy(@RequestBody @Validated PasswordPolicyRequest request) {
        passwordPolicyService.updatePassPolicy(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('VIEW_PASSWORD_POLICY')")
    @Operation(summary = "Get All Password Policies")
    public List<Map<String, Object>> getPasswordPolicies() {
        return passwordPolicyService.getPasswordPolicies();
    }
}
