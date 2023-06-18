package com.usb.pss.ipaservice.admin.controller;
import com.usb.pss.ipaservice.admin.dto.request.ActionRequest;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminAction;
import com.usb.pss.ipaservice.admin.service.impl.ActionServiceImpl;
import com.usb.pss.ipaservice.common.GlobalApiResponse;
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

    @PostMapping("/save")
    public ResponseEntity<IpaAdminAction> saveAction(@RequestBody ActionRequest actionRequest){
        return new ResponseEntity<>(actionService.saveUserAction(actionRequest),HttpStatus.OK);
    }

    @GetMapping("/get-action/{actionId}")
    public ResponseEntity<IpaAdminAction> getAction(@PathVariable Long actionId){
        return new ResponseEntity<>(actionService.getUserAction(actionId),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{actionId}")
    public ResponseEntity<String> deleteAction(@PathVariable Long actionId){
        return new ResponseEntity<>(actionService.deleteUserAction(actionId),HttpStatus.OK);
    }

}
