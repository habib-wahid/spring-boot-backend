package com.usb.pss.ipaservice.admin.controller;

import com.usb.pss.ipaservice.admin.dto.request.RoleMenuRequest;
import com.usb.pss.ipaservice.admin.dto.request.RoleRequest;
import com.usb.pss.ipaservice.admin.dto.response.RoleResponse;
import com.usb.pss.ipaservice.admin.service.iservice.RoleService;
import com.usb.pss.ipaservice.common.GlobalApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

import static com.usb.pss.ipaservice.common.APIEndpointConstants.ROLE_ENDPOINT;

/**
 * @author Junaid Khan Pathan
 * @date Jun 17, 2023
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(ROLE_ENDPOINT)
@Tag(name = "Role Controller", description = "API Endpoints for user role related operations.")
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    @Operation(summary = "Get all active roles in a list.")
    public GlobalApiResponse<List<RoleResponse>> getAllRoles() {
        List<RoleResponse> allActiveRoles = roleService.getAllRoleResponse();
        return new GlobalApiResponse<>(allActiveRoles);
    }

    @GetMapping("/{roleId}")
    @Operation(summary = "Get a single role with it's ID.")
    public GlobalApiResponse<RoleResponse> getRoleById(@PathVariable Long roleId) {
        RoleResponse role = roleService.getRoleResponseById(roleId);
        return new GlobalApiResponse<>(role);
    }

    @PostMapping
    @Operation(summary = "Create a new role with valid role data.")
    public GlobalApiResponse<Void> createNewRole(@Validated @RequestBody RoleRequest roleRequest) {
        roleService.createNewRole(roleRequest);
        return new GlobalApiResponse<>(HttpStatus.CREATED, "Role created successfully.", null);
    }

    @PutMapping("/{roleId}")
    @Operation(summary = "update an existing role with valid role data and existing role's ID.")
    public GlobalApiResponse<Void> updateRole(@Validated @RequestBody RoleRequest roleRequest,
                                              @PathVariable Long roleId) {
        roleService.updateRole(roleRequest, roleId);
        return new GlobalApiResponse<>(HttpStatus.OK, "Role updated successfully.", null);
    }

    @PatchMapping("/{roleId}")
    @Operation(summary = "Deactivate an active role with it's ID.")
    public GlobalApiResponse<Void> deactivateRole(@PathVariable Long roleId) {
        roleService.deactivateRole(roleId);
        return new GlobalApiResponse<>(HttpStatus.OK, "Role deactivated successfully.", null);
    }

    @PostMapping("/{roleId}/menus")
    @Operation(summary = "Add a list of menus to a role with it's ID.")
    public GlobalApiResponse<Void> addRoleMenus(@Validated @RequestBody RoleMenuRequest roleMenuRequest,
                                                @PathVariable Long roleId) {
        roleService.addRoleMenu(roleId, roleMenuRequest);
        return new GlobalApiResponse<>(HttpStatus.OK, "Role menus added successfully.", null);
    }

    @PatchMapping("/{roleId}/menus")
    @Operation(summary = "Remove a list of menus to a role with it's ID.")
    public GlobalApiResponse<Void> removeRoleMenus(@Validated @RequestBody RoleMenuRequest roleMenuRequest,
                                                   @PathVariable Long roleId) {
        roleService.removeRoleMenu(roleId, roleMenuRequest);
        return new GlobalApiResponse<>(HttpStatus.OK, "Role menus removed successfully.", null);
    }
}
