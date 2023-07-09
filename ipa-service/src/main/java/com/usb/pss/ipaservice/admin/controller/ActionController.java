package com.usb.pss.ipaservice.admin.controller;

import com.usb.pss.ipaservice.admin.dto.request.ActionRequest;
import com.usb.pss.ipaservice.admin.dto.response.AdminActionResponse;
import com.usb.pss.ipaservice.admin.dto.response.ModuleResponse;
import com.usb.pss.ipaservice.admin.service.impl.ActionServiceImpl;
import com.usb.pss.ipaservice.admin.service.iservice.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


import static com.usb.pss.ipaservice.common.APIEndpointConstants.ACTION_ENDPOINT;

@RestController
@RequiredArgsConstructor
@RequestMapping(ACTION_ENDPOINT)
public class ActionController {

    private final ActionServiceImpl actionService;
    private final ModuleService moduleService;

    @GetMapping("/{actionId}")
    public ResponseEntity<AdminActionResponse> getAction(@PathVariable Long actionId) {
        return new ResponseEntity<>(actionService.getUserActionById(actionId), HttpStatus.OK);
    }

    @GetMapping("/moduleWiseActions")
    public ResponseEntity<List<ModuleResponse>> getModuleWiseUserActions() {
        return new ResponseEntity<>(moduleService.getModuleActions(), HttpStatus.OK);
    }

}
