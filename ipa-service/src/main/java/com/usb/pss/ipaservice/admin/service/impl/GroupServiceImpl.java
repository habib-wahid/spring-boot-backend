package com.usb.pss.ipaservice.admin.service.impl;

import static com.usb.pss.ipaservice.common.ApplicationConstants.DEFAULT_DIRECTION;
import static com.usb.pss.ipaservice.common.ApplicationConstants.DEFAULT_SORT_BY;

import com.usb.pss.ipaservice.admin.dto.PaginationResponse;
import com.usb.pss.ipaservice.admin.dto.request.GroupActionRequest;
import com.usb.pss.ipaservice.admin.dto.request.GroupActivationRequest;
import com.usb.pss.ipaservice.admin.dto.request.GroupCreateRequest;
import com.usb.pss.ipaservice.admin.dto.request.GroupUpdateRequest;
import com.usb.pss.ipaservice.admin.dto.response.GroupResponse;
import com.usb.pss.ipaservice.admin.dto.response.ModuleActionResponse;
import com.usb.pss.ipaservice.admin.model.entity.Action;
import com.usb.pss.ipaservice.admin.model.entity.Group;
import com.usb.pss.ipaservice.admin.repository.ActionRepository;
import com.usb.pss.ipaservice.admin.repository.GroupRepository;
import com.usb.pss.ipaservice.admin.service.iservice.GroupService;
import com.usb.pss.ipaservice.admin.service.iservice.MenuService;
import com.usb.pss.ipaservice.admin.service.iservice.ModuleService;
import com.usb.pss.ipaservice.common.constants.ExceptionConstant;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import com.usb.pss.ipaservice.exception.RuleViolationException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * @author Junaid Khan Pathan
 * @date Jun 17, 2023
 */

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final MenuService menuService;
    private final ActionRepository actionRepository;
    private final ModuleService moduleService;

    @Override
    public void createNewGroup(GroupCreateRequest groupCreateRequest) {
        Optional<Group> duplicateGroupName = groupRepository.findByNameIgnoreCase(groupCreateRequest.name());
        if (duplicateGroupName.isPresent()) {
            throw new RuleViolationException(ExceptionConstant.DUPLICATE_GROUP_NAME);
        }

        Group groupToSave = new Group();
        prepareEntity(groupCreateRequest, groupToSave);
        groupRepository.save(groupToSave);
    }

    private Group getGroupById(Long groupId) {
        return groupRepository.findById(groupId)
            .orElseThrow(() -> new ResourceNotFoundException(ExceptionConstant.GROUP_NOT_FOUND));
    }

    @Override
    public GroupResponse getGroupResponseById(Long groupId) {
        Group group = getGroupById(groupId);
        return prepareResponse(group);
    }

    @Override
    public PaginationResponse<GroupResponse> getAllGroupResponse(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(DEFAULT_DIRECTION, DEFAULT_SORT_BY));
        Page<Group> groupPage = groupRepository.findAll(pageable);

        return new PaginationResponse<>(
            groupPage.getPageable().getPageNumber(),
            groupPage.getPageable().getPageSize(),
            groupPage.getTotalElements(),
            groupPage.getContent()
                .stream()
                .map(this::prepareResponse)
                .toList(),
            Map.of(
                    "name",  "Group/Role Name",
                    "active", "Status"
            )
        );
    }

    @Override
    public void updateGroup(GroupUpdateRequest groupUpdateRequest) {
        Group groupToUpdate = getGroupById(groupUpdateRequest.id());
        if (!groupToUpdate.getName().equals(groupUpdateRequest.name())) {
            Optional<Group> duplicateGroupName = groupRepository.findByNameIgnoreCase(groupUpdateRequest.name());
            if (duplicateGroupName.isPresent()) {
                throw new RuleViolationException(ExceptionConstant.DUPLICATE_GROUP_NAME);
            }
        }

        prepareEntity(groupUpdateRequest, groupToUpdate);
        groupRepository.save(groupToUpdate);
    }

    @Override
    public void updateGroupActivationStatus(GroupActivationRequest request) {
        Group group = getGroupById(request.groupId());
        group.setActive(request.active());
        groupRepository.save(group);
    }

    @Override
    public void updateGroupWiseAction(GroupActionRequest request) {
        Group group = findGroupAndFetchActionsById(request.groupId());

        List<Action> updatedActions = actionRepository.findActionAndFetchMenuByIdIn(request.actionIds());
        group.getPermittedActions().addAll(updatedActions);
        group.getPermittedActions().retainAll(updatedActions);

        groupRepository.save(group);
    }


    @Override
    public List<ModuleActionResponse> getGroupWisePermittedActions(Long groupId) {
        return moduleService.getAllModulesByGroup(groupId);
    }

    private void prepareEntity(GroupCreateRequest groupCreateRequest, Group group) {
        group.setName(groupCreateRequest.name());
        group.setDescription(groupCreateRequest.description());
        group.setActive(groupCreateRequest.active());
    }

    private void prepareEntity(GroupUpdateRequest groupUpdateRequest, Group group) {
        group.setName(groupUpdateRequest.name());
        group.setDescription(groupUpdateRequest.description());
        group.setActive(groupUpdateRequest.active());
    }

    private GroupResponse prepareResponse(Group group) {
        GroupResponse groupResponse = new GroupResponse();
        groupResponse.setId(group.getId());
        groupResponse.setName(group.getName());
        groupResponse.setDescription(group.getDescription());
        groupResponse.setActive(group.isActive());
        return groupResponse;
    }

    private Group findGroupAndFetchActionsById(Long groupId) {
        return groupRepository.findGroupAndFetchActionsById(groupId)
            .orElseThrow(() -> new ResourceNotFoundException(ExceptionConstant.GROUP_NOT_FOUND));
    }
}
