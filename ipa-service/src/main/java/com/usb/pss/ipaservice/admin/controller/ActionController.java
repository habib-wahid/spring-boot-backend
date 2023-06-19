package com.usb.pss.ipaservice.admin.controller;
import com.usb.pss.ipaservice.admin.dto.request.ActionRequest;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminAction;
import com.usb.pss.ipaservice.admin.service.impl.ActionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<IpaAdminAction> getAction(@PathVariable Long actionId){
        return new ResponseEntity<>(actionService.getUserActionById(actionId),HttpStatus.OK);
    }

    @DeleteMapping("/{actionId}")
    public ResponseEntity<String> deleteAction(@PathVariable Long actionId){
        return new ResponseEntity<>(actionService.deleteUserActionById(actionId),HttpStatus.OK);
    }

}
