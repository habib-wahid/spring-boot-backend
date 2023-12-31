package com.usb.pss.ipaservice.admin.controller;

import com.usb.pss.ipaservice.admin.dto.PaginationResponse;
import com.usb.pss.ipaservice.admin.dto.request.AdditionalActionPermissionRequest;
import com.usb.pss.ipaservice.admin.dto.request.RegistrationRequest;
import com.usb.pss.ipaservice.admin.dto.request.UserActionRequest;
import com.usb.pss.ipaservice.admin.dto.request.UpdateUserInfoRequest;
import com.usb.pss.ipaservice.admin.dto.request.UserGroupRequest;
import com.usb.pss.ipaservice.admin.dto.request.UserStatusRequest;
import com.usb.pss.ipaservice.admin.dto.request.ChangePasswordRequest;
import com.usb.pss.ipaservice.admin.dto.response.ModuleActionResponse;
import com.usb.pss.ipaservice.admin.dto.response.UserProfileResponse;
import com.usb.pss.ipaservice.admin.dto.response.UserGroupResponse;
import com.usb.pss.ipaservice.admin.dto.response.UserResponse;
import com.usb.pss.ipaservice.admin.service.iservice.ModuleService;
import com.usb.pss.ipaservice.admin.service.iservice.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.usb.pss.ipaservice.common.ApplicationConstants.DEFAULT_PAGE_NUMBER;
import static com.usb.pss.ipaservice.common.ApplicationConstants.DEFAULT_PAGE_SIZE;
import static com.usb.pss.ipaservice.common.constants.APIEndpointConstants.USER_ENDPOINT;

@RestController
@RequestMapping(USER_ENDPOINT)
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "API Endpoints for user related operations.")
public class UserController {

    private final UserService userService;
    private final ModuleService moduleService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('CREATE_USER')")
    @Operation(summary = "Create a new user.")
    public void createNewUser(@Validated @RequestBody RegistrationRequest request) {
        userService.createNewUser(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('VIEW_USER')")
    @Operation(summary = "Get all users in a list")
    public PaginationResponse<UserResponse> getAllUsers(
            @RequestParam(name = "page", defaultValue = DEFAULT_PAGE_NUMBER) int pageNumber,
            @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        return userService.getAllUsers(pageNumber, pageSize);
    }

    @GetMapping("/groups")
    @PreAuthorize("hasAnyAuthority('VIEW_USER')")
    @Operation(summary = "Get all users with groups in a list")
    public List<UserGroupResponse> getAllUserWithGroupInfo() {
        return userService.getAllUserWithGroupInfo();
    }


    @PutMapping("/additionalActions")
    @PreAuthorize("hasAnyAuthority('UPDATE_USER_ADDITIONAL_ACTIONS')")
    @Operation(summary = "Give additional action permission to a user")
    public void addAdditionalAction(
            @RequestBody @Validated UserActionRequest userActionRequest) {
        userService.addAdditionalAction(userActionRequest);
    }

    @GetMapping("/{userId}/actions")
    @PreAuthorize("hasAnyAuthority('VIEW_USER')")
    @Operation(summary = "Retrieve module-wise action list for a user ")
    public List<ModuleActionResponse> getModuleWiseUserActions(@PathVariable Long userId) {
        return userService.getModuleWiseUserActions(userId);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Retrieve user information with its id")
    public UserProfileResponse getUserPersonalInfo(@Validated @PathVariable Long userId) {
        return userService.getUserPersonalInfo(userId);
    }

    @PutMapping
    @Operation(summary = "Update existing user information")
    public void updateUserPersonalInfo(@Validated @RequestBody UpdateUserInfoRequest userInfoRequest) {
        userService.updateUserPersonalInfo(userInfoRequest);
    }

    @PutMapping("/groups")
    @PreAuthorize("hasAnyAuthority('UPDATE_USER_GROUP')")
    @Operation(summary = "Update group of a user")
    public void updateUserGroup(
            @RequestBody UserGroupRequest userGroupRequest
    ) {
        userService.updateUserGroup(userGroupRequest);
    }

    @PatchMapping("/updateUserActiveStatus")
    @PreAuthorize("hasAnyAuthority('UPDATE_USER_ACTIVE_STATUS')")
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

    @GetMapping("/additionalActions/{userId}/{moduleId}")
    @Operation(summary = "Get all additional actions of a specific module that are not related to user group")
    public List<ModuleActionResponse> getAllAdditionalActionsWithModules(@PathVariable("userId") Long userId,
                                                                         @PathVariable(name = "moduleId") Long moduleId
    ) {
        return moduleService.getAllAdditionalActionsWithModules(userId, moduleId);
    }

    @PutMapping("/editAdditionalAction")
    @Operation(summary = "Edit user additional Actions of a specific module")
    public void editAdditionalActions(@Validated @RequestBody AdditionalActionPermissionRequest additionalActionPermissionRequest) {
        moduleService.editAdditionalActions(additionalActionPermissionRequest);
    }
}
