package com.usb.pss.ipaservice.admin.controller;

import com.usb.pss.ipaservice.admin.dto.response.AdminActionResponse;
import com.usb.pss.ipaservice.admin.dto.response.ModuleResponse;
import com.usb.pss.ipaservice.admin.service.impl.ActionServiceImpl;
import com.usb.pss.ipaservice.admin.service.iservice.ModuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.usb.pss.ipaservice.common.APIEndpointConstants.ACTION_ENDPOINT;

@RestController
@RequiredArgsConstructor
@RequestMapping(ACTION_ENDPOINT)
@Tag(name = "User Action Controller", description = "API Endpoints for user action related operations.")
public class ActionController {

    private final ActionServiceImpl actionService;
    private final ModuleService moduleService;

    @GetMapping("/{actionId}")
    @Operation(summary = "get user action by id")
    public ResponseEntity<AdminActionResponse> getAction(@PathVariable Long actionId) {
        return new ResponseEntity<>(actionService.getUserActionById(actionId), HttpStatus.OK);
    }

    @GetMapping("/moduleWiseActions")
    @Operation(summary = "get module wise menu and actions")
    public ResponseEntity<List<ModuleResponse>> getModuleWiseUserActions() {
        return new ResponseEntity<>(moduleService.getModuleActions(), HttpStatus.OK);
    }

}
