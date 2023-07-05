package com.usb.pss.ipaservice.admin.controller;

import com.usb.pss.ipaservice.admin.dto.request.ActionRequest;
import com.usb.pss.ipaservice.admin.dto.request.RoleActionRequest;
import com.usb.pss.ipaservice.admin.dto.response.AdminActionResponse;
import com.usb.pss.ipaservice.admin.dto.response.ModuleResponse;
import com.usb.pss.ipaservice.admin.service.impl.ActionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PostMapping()
    public ResponseEntity<String> saveAction(@RequestBody ActionRequest actionRequest) {
        actionService.saveUserAction(actionRequest);
        return new ResponseEntity<>("User action saved", HttpStatus.OK);
    }

    @GetMapping("/{actionId}")
    public ResponseEntity<AdminActionResponse> getAction(@PathVariable Long actionId) {
        return new ResponseEntity<>(actionService.getUserActionById(actionId), HttpStatus.OK);
    }

    @GetMapping("/module-wise-actions")
    public ResponseEntity<List<ModuleResponse>> getModuleWiseUserActions() {
        return new ResponseEntity<>(actionService.getModuleActions(), HttpStatus.OK);
    }

    @DeleteMapping("/{actionId}")
    public ResponseEntity<String> deleteAction(@PathVariable Long actionId) {
        return new ResponseEntity<>(actionService.deleteUserActionById(actionId), HttpStatus.OK);
    }

    @PutMapping("/roleWiseAction")
    public void updateRoleWiseAction(@RequestBody RoleActionRequest request) {
        actionService.updateRoleAction(request);
    }

    @GetMapping("/{roleId}/permittedActions")
    public ResponseEntity<List<ModuleResponse>> getRoleWiseAction(@PathVariable Long roleId) {
        return new ResponseEntity<>(actionService.getRoleWisePermittedActions(roleId), HttpStatus.OK);
    }

}
