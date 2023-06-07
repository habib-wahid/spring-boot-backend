package com.usb.pss.ipaservice.admin.controller;

import com.usb.pss.ipaservice.admin.dto.PermissionDto;
import com.usb.pss.ipaservice.admin.service.PermissionService;
import com.usb.pss.ipaservice.utils.GenericResponse;
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
