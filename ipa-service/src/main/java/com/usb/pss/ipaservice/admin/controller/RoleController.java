package com.usb.pss.ipaservice.admin.controller;

import com.usb.pss.ipaservice.admin.dto.request.RoleActionRequest;
import com.usb.pss.ipaservice.admin.dto.request.RoleMenuRequest;
import com.usb.pss.ipaservice.admin.dto.request.RoleRequest;
import com.usb.pss.ipaservice.admin.dto.response.ModuleResponse;
import com.usb.pss.ipaservice.admin.dto.response.RoleResponse;
import com.usb.pss.ipaservice.admin.service.iservice.RoleService;
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
    public List<RoleResponse> getAllRoles() {
        return roleService.getAllRoleResponse();
    }

    @GetMapping("/{roleId}")
    @Operation(summary = "Get a single role with it's ID.")
    public RoleResponse getRoleById(@PathVariable Long roleId) {
        return roleService.getRoleResponseById(roleId);
    }

    @PostMapping
    @Operation(summary = "Create a new role with valid role data.")
    public void createNewRole(@Validated @RequestBody RoleRequest roleRequest) {
        roleService.createNewRole(roleRequest);
    }

    @PutMapping("/{roleId}")
    @Operation(summary = "update an existing role with valid role data and existing role's ID.")
    public void updateRole(@Validated @RequestBody RoleRequest roleRequest,
                                              @PathVariable Long roleId) {
        roleService.updateRole(roleRequest, roleId);
    }

    @PatchMapping("/{roleId}")
    @Operation(summary = "Deactivate an active role with it's ID.")
    public void deactivateRole(@PathVariable Long roleId) {
        roleService.deactivateRole(roleId);
    }

    @PostMapping("/{roleId}/menus")
    @Operation(summary = "Add a list of menus to a role with it's ID.")
    public void addRoleMenus(@Validated @RequestBody RoleMenuRequest roleMenuRequest,
                                                @PathVariable Long roleId) {
        roleService.addRoleMenu(roleId, roleMenuRequest);
    }

    @PatchMapping("/{roleId}/menus")
    @Operation(summary = "Remove a list of menus to a role with it's ID.")
    public void removeRoleMenus(@Validated @RequestBody RoleMenuRequest roleMenuRequest,
                                                   @PathVariable Long roleId) {
        roleService.removeRoleMenu(roleId, roleMenuRequest);
    }

    @PutMapping("/roleWiseAction")
    public void updateRoleWiseAction(@RequestBody @Validated RoleActionRequest request) {
        roleService.updateRoleAction(request);
    }

    @GetMapping("/{roleId}/roleWiseAction")
    public List<ModuleResponse> getRoleWiseAction(@PathVariable Long roleId) {
        return roleService.getRoleWisePermittedActions(roleId);
    }
}
