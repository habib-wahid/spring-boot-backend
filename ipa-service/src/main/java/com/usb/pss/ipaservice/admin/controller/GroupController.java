package com.usb.pss.ipaservice.admin.controller;

import com.usb.pss.ipaservice.admin.dto.request.GroupActionRequest;
import com.usb.pss.ipaservice.admin.dto.request.GroupCreateRequest;
import com.usb.pss.ipaservice.admin.dto.request.GroupUpdateRequest;
import com.usb.pss.ipaservice.admin.dto.response.ModuleResponse;
import com.usb.pss.ipaservice.admin.dto.response.GroupResponse;
import com.usb.pss.ipaservice.admin.service.iservice.GroupService;
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

import static com.usb.pss.ipaservice.common.APIEndpointConstants.GROUP_ENDPOINT;

/**
 * @author Junaid Khan Pathan
 * @date Jun 17, 2023
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(GROUP_ENDPOINT)
@Tag(name = "Group Controller", description = "API Endpoints for user group related operations.")
public class GroupController {
    private final GroupService groupService;

    @GetMapping
    @Operation(summary = "Get active Groups in a list.")
    public List<GroupResponse> getAllGroups() {
        return groupService.getAllGroupResponse();
    }

    @GetMapping("/{groupId}")
    @Operation(summary = "Get a single Group with it's ID.")
    public GroupResponse getGroupById(@Validated @PathVariable Long groupId) {
        return groupService.getGroupResponseById(groupId);
    }

    @PostMapping
    @Operation(summary = "Create a new Group with valid Group data.")
    public void createNewGroup(@Validated @RequestBody GroupCreateRequest groupCreateRequest) {
        groupService.createNewGroup(groupCreateRequest);
    }

    @PutMapping
    @Operation(summary = "update an existing Group with valid Group data and existing Group's ID.")
    public void updateGroup(@Validated @RequestBody GroupUpdateRequest groupCreateRequest) {
        groupService.updateGroup(groupCreateRequest);
    }

    @PatchMapping("/{groupId}")
    @Operation(summary = "Deactivate an active Group with it's ID.")
    public void deactivateGroup(@PathVariable Long groupId) {
        groupService.deactivateGroup(groupId);
    }

    @PutMapping("/groupWiseAction")
    @Operation(summary = "Update actions of a Group")
    public void updateGroupWiseAction(@RequestBody @Validated GroupActionRequest request) {
        groupService.updateGroupWiseAction(request);
    }

    @GetMapping("/{groupId}/GroupWiseAction")
    @Operation(summary = "Get actions of a Group with it's ID")
    public List<ModuleResponse> getGroupWiseAction(@PathVariable Long groupId) {
        return groupService.getGroupWisePermittedActions(groupId);
    }
}
