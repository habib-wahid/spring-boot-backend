package com.usb.pss.ipaservice.admin.controller;

import com.usb.pss.ipaservice.admin.dto.request.RegistrationRequest;
import com.usb.pss.ipaservice.admin.dto.request.UserGroupRequest;
import com.usb.pss.ipaservice.admin.dto.request.UserMenuRequest;
import com.usb.pss.ipaservice.admin.dto.response.MenuResponse;
import com.usb.pss.ipaservice.admin.dto.response.UserResponse;
import com.usb.pss.ipaservice.admin.service.iservice.UserService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Set;
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
//        userService.updateGroup(request);
    }

    @PutMapping("/group/remove")
    @Operation(summary = "Remove a user from a group.")
    public void removeFromGroup(@RequestBody @Validated UserGroupRequest request) {
//        userService.removeGroup(request);
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

    @PostMapping("/{userId}/menus")
    @Operation(summary = "add a set of menu to a user")
    public void addUserMenus(@PathVariable Long userId,
                             @RequestBody @Validated UserMenuRequest userMenuRequest) {
        userService.addUserMenus(userId, userMenuRequest);
    }

    @PatchMapping("/{userId}/menus")
    @Operation(summary = "Remove a set of menu from a user")
    public void removeUserMenus(@PathVariable Long userId,
                                @RequestBody @Validated UserMenuRequest userMenuRequest) {
        userService.removeUserMenus(userId, userMenuRequest);
    }

}
