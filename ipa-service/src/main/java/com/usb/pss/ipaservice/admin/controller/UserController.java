package com.usb.pss.ipaservice.admin.controller;

import com.usb.pss.ipaservice.admin.dto.request.RegistrationRequest;
import com.usb.pss.ipaservice.admin.dto.request.UserGroupRequest;
import com.usb.pss.ipaservice.admin.dto.response.UserResponse;
import com.usb.pss.ipaservice.admin.service.iservice.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.usb.pss.ipaservice.common.APIEndpointConstants.USER_ENDPOINT;

@RestController
@RequestMapping(USER_ENDPOINT)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Register a new user.")
    public void registerUser(@RequestBody @Validated RegistrationRequest request) {
        userService.registerUser(request);
    }

    @PutMapping("/group/assign")
    @Operation(summary = "Assign a user to a group.")
    public void assignGroup(@RequestBody @Validated UserGroupRequest request) {
        userService.updateGroup(request);
    }

    @PutMapping("/group/remove")
    @Operation(summary = "Remove a user from a group.")
    public void removeFromGroup(@RequestBody @Validated UserGroupRequest request) {
        userService.removeGroup(request);
    }

    @GetMapping
    @Operation(summary = "Get all users in a list")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

}
