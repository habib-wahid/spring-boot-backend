package com.usb.pss.ipaservice.admin.controller;

import com.usb.pss.ipaservice.admin.dto.request.RegistrationRequest;
import com.usb.pss.ipaservice.admin.dto.request.UserActionRequest;
import com.usb.pss.ipaservice.admin.dto.request.UserRoleActionRequest;
import com.usb.pss.ipaservice.admin.dto.response.MenuResponse;
import com.usb.pss.ipaservice.admin.dto.response.ModuleResponse;
import com.usb.pss.ipaservice.admin.dto.response.UserResponse;
import com.usb.pss.ipaservice.admin.service.iservice.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

import static com.usb.pss.ipaservice.common.APIEndpointConstants.USER_ENDPOINT;

@RestController
@RequestMapping(USER_ENDPOINT)
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "API Endpoints for user related operations.")
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Register a new user.")
    public void registerUser(@RequestBody @Validated RegistrationRequest request) {
        userService.registerUser(request);
    }

    @GetMapping
    @Operation(summary = "Get all users in a list")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/permitted/menus")
    @Operation(summary = "Get all permitted menus by current logged in user")
    public Set<MenuResponse> getMenuByUserId() {
        return userService.getUserAllPermittedMenu();
    }

    @PutMapping("/actions")
    @Operation(summary = "Update actions for a user")
    public void updateUserActions(
            @RequestBody @Validated UserActionRequest userActionRequest) {
        userService.updateUserActions(userActionRequest);
    }

    @GetMapping("/{userId}/actions")
    @Operation(summary = "Retrieve the list of actions module wise for a user ")
    public List<ModuleResponse> getModuleWiseUserActions(@PathVariable Long userId) {
        return userService.getModuleWiseUserActions(userId);
    }

    @PutMapping("/roles")
    @Operation(summary = "Update user role action and menu")
    public void updateUserRole(
            @RequestBody UserRoleActionRequest userRoleActionRequest
    ) {
        userService.updateUserRole(userRoleActionRequest);
    }
}
