package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.request.GroupActionRequest;
import com.usb.pss.ipaservice.admin.dto.request.GroupRequest;
import com.usb.pss.ipaservice.admin.dto.response.ModuleResponse;
import com.usb.pss.ipaservice.admin.dto.response.GroupResponse;
import com.usb.pss.ipaservice.admin.model.entity.Action;
import com.usb.pss.ipaservice.admin.model.entity.Group;
import com.usb.pss.ipaservice.admin.repository.ActionRepository;
import com.usb.pss.ipaservice.admin.repository.GroupRepository;
import com.usb.pss.ipaservice.admin.service.iservice.MenuService;
import com.usb.pss.ipaservice.admin.service.iservice.ModuleService;
import com.usb.pss.ipaservice.admin.service.iservice.GroupService;
import com.usb.pss.ipaservice.common.ExceptionConstant;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import com.usb.pss.ipaservice.exception.RuleViolationException;
import lombok.RequiredArgsConstructor;
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
    public void createNewGroup(GroupRequest groupRequest) {
        Optional<Group> duplicateGroupName = groupRepository.findByNameIgnoreCase(groupRequest.name());
        if (duplicateGroupName.isPresent()) {
            throw new RuleViolationException(ExceptionConstant.DUPLICATE_GROUP_NAME);
        }

        Group groupToSave = new Group();
        prepareEntity(groupRequest, groupToSave);
        groupRepository.save(groupToSave);
    }

    @Override
    public Group getGroupById(Long groupId) {
        return groupRepository.findById(groupId)
            .orElseThrow(() -> new ResourceNotFoundException(ExceptionConstant.GROUP_NOT_FOUND));
    }

    @Override
    public GroupResponse getGroupResponseById(Long groupId) {
        Group group = getGroupById(groupId);
        GroupResponse groupResponse = new GroupResponse();
        prepareResponse(group, groupResponse);
        return groupResponse;
    }

    @Override
    public List<GroupResponse> getAllGroupResponse() {
        return groupRepository.findAllByOrderByCreatedDateDesc().stream()
            .map(group -> {
                GroupResponse groupResponse = new GroupResponse();
                prepareResponse(group, groupResponse);
                return groupResponse;
            }).toList();
    }

    @Override
    public void updateGroup(GroupRequest groupRequest, Long groupId) {
        Group groupToUpdate = getGroupById(groupId);
        if (!groupToUpdate.getName().equals(groupRequest.name())) {
            Optional<Group> duplicateGroupName = groupRepository.findByNameIgnoreCase(groupRequest.name());
            if (duplicateGroupName.isPresent()) {
                throw new RuleViolationException(ExceptionConstant.DUPLICATE_GROUP_NAME);
            }
        }

        prepareEntity(groupRequest, groupToUpdate);
        groupRepository.save(groupToUpdate);
    }

    @Override
    public void deactivateGroup(Long groupId) {
        //TODO will be implemented later if needed
    }

    @Override
    public void updateGroupAction(GroupActionRequest request) {
        Group group = groupRepository.findGroupAndFetchMenuAndActionsById(request.groupId())
            .orElseThrow(() -> new ResourceNotFoundException(ExceptionConstant.GROUP_NOT_FOUND));

        List<Action> updatedActions = actionRepository.findActionAndFetchMenuByIdIn(request.actionIds());
        group.getPermittedActions().addAll(updatedActions);
        group.getPermittedActions().retainAll(updatedActions);

        groupRepository.save(group);
    }

    @Override
    public List<ModuleResponse> getGroupWisePermittedActions(Long groupId) {
        return moduleService.getAllModulesByGroup(groupId);
    }

    private void prepareEntity(GroupRequest groupRequest, Group group) {
        group.setName(groupRequest.name());
        group.setDescription(groupRequest.description());
    }

    private void prepareResponse(Group group, GroupResponse groupResponse) {
        groupResponse.setId(group.getId());
        groupResponse.setName(group.getName());
        groupResponse.setDescription(group.getDescription());
    }
}
