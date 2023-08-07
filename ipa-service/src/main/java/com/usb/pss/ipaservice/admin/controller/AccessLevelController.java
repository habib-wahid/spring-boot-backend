package com.usb.pss.ipaservice.admin.controller;

import com.usb.pss.ipaservice.admin.dto.request.CreateAccessLevelRequest;
import com.usb.pss.ipaservice.admin.dto.request.UpdateAccessLevelRequest;
import com.usb.pss.ipaservice.admin.model.entity.AccessLevel;
import com.usb.pss.ipaservice.admin.service.iservice.AccessLevelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.usb.pss.ipaservice.common.constants.APIEndpointConstants.ACCESS_LEVEL_ENDPOINT;

@RestController
@RequiredArgsConstructor
@RequestMapping(ACCESS_LEVEL_ENDPOINT)
@Tag(name = "Access Level Controller", description = "API Endpoints for access level related operations.")
public class AccessLevelController {
    private final AccessLevelService accessLevelService;

    @GetMapping
    @Operation(summary = "Get all access levels")
    public List<AccessLevel> getAccessLevels() {
        return accessLevelService.getAllAccessLevels();
    }

    @PostMapping
    @Operation(summary = "Create a new access level")
    public void createAccessLevel(@RequestBody @Validated CreateAccessLevelRequest createAccessLevelRequest) {
        accessLevelService.createAccessLevel(createAccessLevelRequest);
    }

    @PutMapping
    @Operation(summary = "Update an existing access level")
    public void updateAccessLevel(@RequestBody @Validated UpdateAccessLevelRequest updateAccessLevelRequest) {
        accessLevelService.updateAccessLevel(updateAccessLevelRequest);
    }
}
