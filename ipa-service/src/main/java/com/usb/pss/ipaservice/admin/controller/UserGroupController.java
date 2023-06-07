package com.usb.pss.ipaservice.admin.controller;

import com.usb.pss.ipaservice.admin.dto.GroupPermissionDto;
import com.usb.pss.ipaservice.admin.dto.GroupsDto;
import com.usb.pss.ipaservice.admin.dto.SaveGroupPermissionRequest;
import com.usb.pss.ipaservice.admin.dto.SaveSingleGroupPermissionsRequest;
import com.usb.pss.ipaservice.admin.dto.SaveSingleUserGroupsRequest;
import com.usb.pss.ipaservice.admin.dto.SaveUserGroupRequest;
import com.usb.pss.ipaservice.admin.dto.UserGroupDto;
import com.usb.pss.ipaservice.admin.service.GroupPermissionService;
import com.usb.pss.ipaservice.admin.service.GroupService;
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
public class UserGroupController {

    private final GroupService groupService;
    private final GroupPermissionService groupPermissionService;


    @PostMapping(path = "/groups/save")
    public GenericResponse saveGroup(@RequestBody GroupsDto request) {
        return groupService.saveGroup(request);
    }

    @GetMapping(path = "/groups/get")
    public List<GroupsDto> getGroups() {
        return groupService.getGroups();
    }

    @Operation(
            description = "For every object in the list, user and group entity is retrieved",
            summary = "User-Group save endpoint"
    )
    @PostMapping(path = "/user-group/save")
    public GenericResponse saveUserGroup(@RequestBody SaveUserGroupRequest request) {
        return groupService.saveUserGroup(request);
    }

    @GetMapping("/user-group/get/{userId}")
    public List<UserGroupDto> getUserGroup(@PathVariable Long userId) {
        return groupService.getUserGroups(userId);
    }

    @PostMapping(path = "/group-permission/save")
    public GenericResponse saveGroupPermission(@RequestBody SaveGroupPermissionRequest request) {
        return groupPermissionService.saveGroupPermission(request);
    }

    @GetMapping("/group-permission/get/{groupId}")
    public List<GroupPermissionDto> getGroupPermission(@PathVariable Long groupId) {
        return groupPermissionService.getGroupPermissions(groupId);
    }

    @Operation(
            description = "For every object in the list, only group entity is retrieved",
            summary = "Single User-wise Group save endpoint (For Frontend)"
    )
    @PostMapping(path = "/single-user/groups/save")
    public GenericResponse saveSingleUserGroups(@RequestBody SaveSingleUserGroupsRequest request) {
        return groupService.saveSingleUserGroups(request);
    }

    @Operation(
            description = "For every object in the list, only permission entity is retrieved",
            summary = "Single Group-wise Permission save endpoint (For Frontend)"
    )
    @PostMapping(path = "/single-group/permissions/save")
    public GenericResponse saveSingleGroupPermissions(@RequestBody SaveSingleGroupPermissionsRequest request) {
        return groupPermissionService.saveSingleGroupPermissions(request);
    }
}
