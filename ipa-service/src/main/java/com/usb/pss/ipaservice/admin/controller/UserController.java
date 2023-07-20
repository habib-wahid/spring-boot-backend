package com.usb.pss.ipaservice.admin.controller;

import com.usb.pss.ipaservice.admin.dto.request.ChangePasswordRequest;
import com.usb.pss.ipaservice.admin.dto.request.RegistrationRequest;
import com.usb.pss.ipaservice.admin.dto.request.UpdateUserInfoRequest;
import com.usb.pss.ipaservice.admin.dto.request.UserActionRequest;
import com.usb.pss.ipaservice.admin.dto.request.UserGroupRequest;
import com.usb.pss.ipaservice.admin.dto.request.UserStatusRequest;
import com.usb.pss.ipaservice.admin.dto.response.MenuResponse;
import com.usb.pss.ipaservice.admin.dto.response.ModuleResponse;
import com.usb.pss.ipaservice.admin.dto.response.UserPersonalInfoResponse;
import com.usb.pss.ipaservice.admin.dto.response.UserResponse;
import com.usb.pss.ipaservice.admin.service.iservice.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    @Operation(summary = "Create a new user.")
    public void createNewUser(@Validated @RequestBody RegistrationRequest request) {
        userService.createNewUser(request);
    }

    @GetMapping
    @Operation(summary = "Get all users in a list")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/permittedMenus")
    @Operation(summary = "Get all permitted menus by current logged in user")
    public Set<MenuResponse> getPermittedMenusByUserId() {
        return userService.getUserAllPermittedMenu();
    }

    @PutMapping("/additionalActions")
    @Operation(summary = "Give additional action permission to a user")
    public void addAdditionalAction(
        @RequestBody @Validated UserActionRequest userActionRequest) {
        userService.addAdditionalAction(userActionRequest);
    }

    @GetMapping("/{userId}/actions")
    @Operation(summary = "Retrieve module-wise action list for a user ")
    public List<ModuleResponse> getModuleWiseUserActions(@PathVariable Long userId) {
        return userService.getModuleWiseUserActions(userId);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Retrieve user information with its id")
    public UserPersonalInfoResponse getUserPersonalInfo(@Validated @PathVariable Long userId) {
        return userService.getUserPersonalInfo(userId);
    }

    @PutMapping
    @Operation(summary = "Update existing user information")
    public void updateUserPersonalInfo(@Validated @RequestBody UpdateUserInfoRequest userInfoRequest) {
        userService.updateUserPersonalInfo(userInfoRequest);
    }

    @PutMapping("/groups")
    @Operation(summary = "Update group of a user")
    public void updateUserGroup(
        @RequestBody UserGroupRequest userGroupRequest
    ) {
        userService.updateUserGroup(userGroupRequest);
    }

    @PatchMapping("/updateUserActiveStatus")
    @Operation(summary = "Update user activation status")
    public void updateUserActiveStatus(@Validated @RequestBody UserStatusRequest userStatusRequest) {
        userService.updateUserStatusInfo(userStatusRequest);
    }

    @PutMapping("/changePassword")
    @Operation(summary = "Update user password")
    public void changeUserPassword(
        @RequestBody ChangePasswordRequest changePasswordRequest
    ) {
        userService.changeUserPassword(changePasswordRequest);
    }
}
