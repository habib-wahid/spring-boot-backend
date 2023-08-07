package com.usb.pss.ipaservice.admin.controller;

import com.usb.pss.ipaservice.admin.dto.response.UserTypeResponse;
import com.usb.pss.ipaservice.admin.service.iservice.UserTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.usb.pss.ipaservice.common.constants.APIEndpointConstants.USER_TYPE_ENDPOINT;

@RestController
@RequestMapping(USER_TYPE_ENDPOINT)
@RequiredArgsConstructor
@Tag(name = "User Type Controller", description = "API Endpoints for user type related operations.")
public class UserTypeController {
    private final UserTypeService userTypeService;

    @GetMapping
    @Operation(summary = "Get all user Types in a list")
    public List<UserTypeResponse> getUserTypeById() {
        return userTypeService.getAllUserTypes();
    }

    @GetMapping({"/userTypeId"})
    @Operation(summary = "Get a User type with it's id")
    public UserTypeResponse getUserTypeById(@RequestParam @Validated Long userTypeId) {
        return userTypeService.getUserTypeResponse(userTypeService.findUserTypeById(userTypeId));
    }

}
