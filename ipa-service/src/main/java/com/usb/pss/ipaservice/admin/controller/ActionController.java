package com.usb.pss.ipaservice.admin.controller;
import com.usb.pss.ipaservice.admin.dto.request.ActionRequest;
import com.usb.pss.ipaservice.admin.dto.response.ActionResponse;
import com.usb.pss.ipaservice.admin.dto.response.AdminActionResponse;
import com.usb.pss.ipaservice.admin.dto.response.MenuActionResponse;
import com.usb.pss.ipaservice.admin.dto.response.ModuleActionResponse;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminAction;
import com.usb.pss.ipaservice.admin.repository.IpadAdminModuleRepository;
import com.usb.pss.ipaservice.admin.service.impl.ActionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.usb.pss.ipaservice.common.APIEndpointConstants.ACTION_ENDPOINT;

@RestController
@RequiredArgsConstructor
@RequestMapping(ACTION_ENDPOINT)
public class ActionController {

    private final ActionServiceImpl actionService;

    @PostMapping()
    public ResponseEntity<String> saveAction(@RequestBody ActionRequest actionRequest){
        actionService.saveUserAction(actionRequest);
        return new ResponseEntity<>("User action saved",HttpStatus.OK);
    }

    @GetMapping("/{actionId}")
    public ResponseEntity<AdminActionResponse> getAction(@PathVariable Long actionId){
        return new ResponseEntity<>(actionService.getUserActionById(actionId),HttpStatus.OK);
    }

    @GetMapping("/module-wise-actions")
    public ResponseEntity<List<ModuleActionResponse>> getModuleWiseUserActions(){
        return new ResponseEntity<>( actionService.getModuleActions(),HttpStatus.OK);
    }
    @DeleteMapping("/{actionId}")
    public ResponseEntity<String> deleteAction(@PathVariable Long actionId){
        return new ResponseEntity<>(actionService.deleteUserActionById(actionId),HttpStatus.OK);
    }

}
