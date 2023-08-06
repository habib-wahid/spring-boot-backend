package com.usb.pss.ipaservice.admin.controller;

import com.usb.pss.ipaservice.admin.dto.PaginationResponse;
import com.usb.pss.ipaservice.admin.dto.request.GroupActionRequest;
import com.usb.pss.ipaservice.admin.dto.request.GroupActivationRequest;
import com.usb.pss.ipaservice.admin.dto.request.GroupCreateRequest;
import com.usb.pss.ipaservice.admin.dto.request.GroupUpdateRequest;
import com.usb.pss.ipaservice.admin.dto.response.GroupResponse;
import com.usb.pss.ipaservice.admin.dto.response.ModuleActionResponse;
import com.usb.pss.ipaservice.admin.service.iservice.GroupService;
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
import static com.usb.pss.ipaservice.common.constants.APIEndpointConstants.GROUP_ENDPOINT;

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
    @PreAuthorize("hasAnyAuthority('VIEW_GROUP')")
    @Operation(summary = "Get active Groups in a list.")
    public PaginationResponse<GroupResponse> getAllGroups(
        @RequestParam(name = "page", defaultValue = DEFAULT_PAGE_NUMBER) int page,
        @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        return groupService.getAllGroupResponse(page, pageSize);
    }

    @GetMapping("/{groupId}")
    @PreAuthorize("hasAnyAuthority('VIEW_GROUP')")
    @Operation(summary = "Get a single Group with it's ID.")
    public GroupResponse getGroupById(@Validated @PathVariable Long groupId) {
        return groupService.getGroupById(groupId);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('CREATE_GROUP')")
    @Operation(summary = "Create a new Group with valid Group data.")
    public void createNewGroup(@Validated @RequestBody GroupCreateRequest groupCreateRequest) {
        groupService.createNewGroup(groupCreateRequest);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('UPDATE_GROUP')")
    @Operation(summary = "update an existing Group with valid Group data and existing Group's ID.")
    public void updateGroup(@Validated @RequestBody GroupUpdateRequest groupUpdateRequest) {
        groupService.updateGroup(groupUpdateRequest);
    }

    @PatchMapping("/updateGroupActivation")
    @PreAuthorize("hasAnyAuthority('UPDATE_GROUP')")
    @Operation(summary = "Update Group Activation Status")
    public void deactivateGroup(@RequestBody @Validated GroupActivationRequest request) {
        groupService.updateGroupActivationStatus(request);
    }

    @PutMapping("/groupWiseAction")
    @PreAuthorize("hasAnyAuthority('UPDATE_GROUP_ACTION_PERMISSION')")
    @Operation(summary = "Update actions of a Group")
    public void updateGroupWiseAction(@RequestBody @Validated GroupActionRequest request) {
        groupService.updateGroupWiseAction(request);
    }

    @GetMapping("/{groupId}/groupWiseAction")
    @PreAuthorize("hasAnyAuthority('VIEW_GROUP')")
    @Operation(summary = "Get actions of a Group with it's ID")
    public List<ModuleActionResponse> getGroupWiseAction(@PathVariable Long groupId) {
        return groupService.getGroupWisePermittedActions(groupId);
    }
}
