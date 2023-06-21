package com.usb.pss.ipaservice.admin.controller;

import com.usb.pss.ipaservice.admin.dto.request.RegistrationRequest;
import com.usb.pss.ipaservice.admin.dto.request.UserGroupRequest;
import com.usb.pss.ipaservice.admin.service.iservice.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.usb.pss.ipaservice.common.APIEndpointConstants.USER_ENDPOINT;

@RestController
@RequestMapping(USER_ENDPOINT)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public void registerUser(@RequestBody @Validated RegistrationRequest request) {
        userService.registerUser(request);
    }

    @PutMapping("/group/assign")
    public void assignGroup(@RequestBody @Validated UserGroupRequest request) {
        userService.updateGroup(request);
    }

    @PutMapping("/group/remove")
    public void removeFromGroup(@RequestBody @Validated UserGroupRequest request) {
        userService.removeGroup(request);
    }

}
