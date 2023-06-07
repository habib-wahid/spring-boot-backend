package com.usb.pss.ipaservice.inventory.controller;

import com.usb.pss.ipaservice.inventory.dto.roleGroupPermission.RoleDto;
import com.usb.pss.ipaservice.inventory.dto.roleGroupPermission.RolePermissionDto;
import com.usb.pss.ipaservice.inventory.dto.roleGroupPermission.SaveRolePermissionRequest;
import com.usb.pss.ipaservice.inventory.dto.roleGroupPermission.SaveSingleRolePermissionsRequest;
import com.usb.pss.ipaservice.inventory.dto.roleGroupPermission.SaveSingleUserRolesRequest;
import com.usb.pss.ipaservice.inventory.dto.roleGroupPermission.SaveUserRoleRequest;
import com.usb.pss.ipaservice.inventory.dto.roleGroupPermission.UserRoleDto;
import com.usb.pss.ipaservice.inventory.service.RolePermissionService;
import com.usb.pss.ipaservice.inventory.service.RoleService;
import com.usb.pss.ipaservice.utils.GenericResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserRoleController {
    private final RoleService roleService;
    private final RolePermissionService rolePermissionService;

    @PostMapping(path = "/role/save")
    public GenericResponse saveRole(@RequestBody RoleDto request) {
        return roleService.saveRole(request);
    }

    @GetMapping(path = "/role/get")
    public List<RoleDto> getRoles() {
        return roleService.getRoles();
    }

    @PostMapping(path = "/user-role/save")
    public GenericResponse saveUserRole(@RequestBody SaveUserRoleRequest request) {
        return roleService.saveUserRole(request);
    }

    @GetMapping("/user-role/get/{userId}")
    public List<UserRoleDto> getUserRole(@PathVariable Long userId) {
        return roleService.getUserRoles(userId);
    }

    @PostMapping(path = "/role-permission/save")
    public GenericResponse saveRolePermission(@RequestBody SaveRolePermissionRequest request) {
        return rolePermissionService.saveRolePermission(request);
    }

    @GetMapping("/role-permission/get/{roleId}")
    public List<RolePermissionDto> getRolePermission(@PathVariable Long roleId) {
        return rolePermissionService.getRolePermissions(roleId);
    }


    @Operation(
            description = "For every object in the list, only role entity is retrieved",
            summary = "Single User-wise Role save endpoint (For Frontend)"
    )
    @PostMapping(path = "/single-user/roles/save")
    public GenericResponse saveSingleUserRoles(@RequestBody SaveSingleUserRolesRequest request) {
        return roleService.saveSingleUserRoles(request);
    }

    @Operation(
            description = "For every object in the list, only permission entity is retrieved",
            summary = "Single Group-wise Permission save endpoint (For Frontend)"
    )
    @PostMapping(path = "/single-role/permissions/save")
    public GenericResponse saveSingleGroupPermissions(@RequestBody SaveSingleRolePermissionsRequest request) {
        return rolePermissionService.saveSingleRolePermissions(request);
    }
}
