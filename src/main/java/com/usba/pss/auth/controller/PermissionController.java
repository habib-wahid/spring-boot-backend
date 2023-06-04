package com.usba.pss.auth.controller;

import com.usba.pss.auth.service.PermissionService;
import com.usba.pss.utils.GenericResponse;
import com.usba.pss.auth.dto.groupRolePermission.PermissionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PermissionController {
    private final PermissionService permissionService;

    @PostMapping(path = "/permission/save")
    public GenericResponse savePermission(@RequestBody PermissionDto request) {
        return permissionService.savePermission(request);
    }

    @GetMapping(path = "/permission/get")
    public List<PermissionDto> getPermissions() {
        return permissionService.getPermissions();
    }

    @GetMapping("/user-permission/get/{userId}")
    public List<PermissionDto> getUserPermission(@PathVariable Long userId) {
        return permissionService.getUserPermissions(userId);
    }
}
