package com.usb.pss.ipaservice.admin.controller;

import com.usb.pss.ipaservice.admin.dto.request.GroupRequest;
import com.usb.pss.ipaservice.admin.dto.request.GroupRoleRequest;
import com.usb.pss.ipaservice.admin.dto.response.GroupResponse;
import com.usb.pss.ipaservice.admin.service.iservice.GroupService;
import com.usb.pss.ipaservice.common.GlobalApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.usb.pss.ipaservice.common.APIEndpointConstants.GROUP_ENDPOINT;

@RestController
@RequiredArgsConstructor
@RequestMapping(GROUP_ENDPOINT)
@Tag(name = "Group Controller", description = "API Endpoints for user group related operations.")
public class GroupController {
    private final GroupService groupService;

    @GetMapping
    @Operation(summary = "Get all active groups in a list.")
    public GlobalApiResponse<List<GroupResponse>> getAllGroups() {
        List<GroupResponse> allGroups = groupService.getAllActiveGroups();
        return new GlobalApiResponse<>(allGroups);
    }

    @GetMapping("/{groupId}")
    @Operation(summary = "Get a single group with it's ID.")
    public GlobalApiResponse<GroupResponse> getGroupById(@PathVariable Long groupId) {
        GroupResponse group = groupService.getGroupResponseById(groupId);
        return new GlobalApiResponse<>(group);
    }

    @PostMapping
    @Operation(summary = "Create a new group with valid group data.")
    public GlobalApiResponse<Void> createNewGroup(@Validated @RequestBody GroupRequest groupRequest) {
        groupService.createNewGroup(groupRequest);
        return new GlobalApiResponse<>(HttpStatus.CREATED, "Group created successfully.", null);
    }

    @PutMapping("/{groupId}")
    @Operation(summary = "update an existing group with valid group data and existing group ID.")
    public GlobalApiResponse<Void> updateGroup(@Validated @RequestBody GroupRequest groupRequest,
                                               @PathVariable Long groupId) {
        groupService.updateGroup(groupRequest, groupId);
        return new GlobalApiResponse<>(HttpStatus.OK, "Group updated successfully.", null);
    }

    @DeleteMapping("/{groupId}")
    @Operation(summary = "Deactivate an active group with it's ID.")
    public GlobalApiResponse<Void> deactivateGroup(@PathVariable Long groupId) {
        groupService.deactivateGroup(groupId);
        return new GlobalApiResponse<>(HttpStatus.OK, "Group deactivated successfully.", null);
    }

    @PostMapping("/{groupId}/roles")
    @Operation(summary = "Add a list of roles to a group with it's ID.")
    public GlobalApiResponse<Void> addGroupRoles(@Validated @RequestBody GroupRoleRequest groupRoleRequest,
                                                 @PathVariable Long groupId) {
        groupService.addGroupRoles(groupId, groupRoleRequest);
        return new GlobalApiResponse<>(HttpStatus.OK, "Group roles added successfully.", null);
    }

    @DeleteMapping("/{groupId}/roles")
    @Operation(summary = "Remove a list of roles to a group with it's ID.")
    public GlobalApiResponse<Void> removeGroupRoles(@Validated @RequestBody GroupRoleRequest groupRoleRequest,
                                                    @PathVariable Long groupId) {
        groupService.removeGroupRoles(groupId, groupRoleRequest);
        return new GlobalApiResponse<>(HttpStatus.OK, "Group roles removed successfully.", null);
    }
}
