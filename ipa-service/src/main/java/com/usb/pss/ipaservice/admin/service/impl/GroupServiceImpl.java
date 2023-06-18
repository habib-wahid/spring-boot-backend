package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.request.GroupRequest;
import com.usb.pss.ipaservice.admin.dto.request.GroupRoleRequest;
import com.usb.pss.ipaservice.admin.dto.response.GroupResponse;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminGroup;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminRole;
import com.usb.pss.ipaservice.admin.repository.IpaAdminGroupRepository;
import com.usb.pss.ipaservice.admin.service.iservice.GroupService;
import com.usb.pss.ipaservice.admin.service.iservice.RoleService;
import com.usb.pss.ipaservice.common.ExceptionConstant;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import com.usb.pss.ipaservice.exception.RuleViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Junaid Khan Pathan
 * @date Jun 17, 2023
 */

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final IpaAdminGroupRepository groupRepository;
    private final RoleService roleService;

    @Override
    public void createNewGroup(GroupRequest groupRequest) {
        Optional<IpaAdminGroup> duplicateGroupName = groupRepository.findActiveGroupByName(groupRequest.name());
        if (duplicateGroupName.isPresent()) {
            throw new RuleViolationException(ExceptionConstant.DUPLICATE_GROUP_NAME);
        }

        IpaAdminGroup groupToSave = new IpaAdminGroup();
        prepareEntity(groupRequest, groupToSave);
        groupRepository.save(groupToSave);
    }

    @Override
    public IpaAdminGroup getGroupById(Long groupId) {
        return groupRepository.findActiveGroupById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionConstant.GROUP_NOT_FOUND));
    }

    @Override
    public IpaAdminGroup getGroupByName(String groupName) {
        return groupRepository.findActiveGroupByName(groupName)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionConstant.GROUP_NOT_FOUND));
    }

    @Override
    public GroupResponse getGroupResponseById(Long groupId) {
        IpaAdminGroup group = getGroupById(groupId);
        GroupResponse groupResponse = new GroupResponse();
        prepareResponse(group, groupResponse);
        return groupResponse;
    }

    @Override
    public List<GroupResponse> getAllActiveGroups() {
        return groupRepository.findAllActiveGroups().stream()
                .map(group -> {
                    GroupResponse groupResponse = new GroupResponse();
                    prepareResponse(group, groupResponse);
                    return groupResponse;
                }).collect(Collectors.toList());
    }

    @Override
    public List<GroupResponse> getAllInactiveGroups() {
        return groupRepository.findAllInactiveGroups().stream()
                .map(group -> {
                    GroupResponse groupResponse = new GroupResponse();
                    prepareResponse(group, groupResponse);
                    return groupResponse;
                }).collect(Collectors.toList());
    }

    @Override
    public void updateGroup(GroupRequest groupRequest, Long groupId) {
        IpaAdminGroup groupToUpdate = getGroupById(groupId);
        Optional<IpaAdminGroup> duplicateGroupName = groupRepository.findActiveGroupByName(groupRequest.name());
        if (duplicateGroupName.isPresent()) {
            throw new RuleViolationException(ExceptionConstant.DUPLICATE_GROUP_NAME);
        }

        prepareEntity(groupRequest, groupToUpdate);
        groupRepository.save(groupToUpdate);
    }

    @Override
    public void deactivateGroup(Long groupId) {
        IpaAdminGroup groupToDeactivate = getGroupById(groupId);
        groupToDeactivate.setActive(false);
        groupRepository.save(groupToDeactivate);
    }

    @Override
    public void addGroupRoles(Long groupId, GroupRoleRequest groupRoleRequest) {
        IpaAdminGroup group = getGroupById(groupId);
        List<IpaAdminRole> roleList = groupRoleRequest.groupRoleIds().stream()
                .map(roleService::getRoleById).toList();
        roleList.forEach(group::addRole);
        groupRepository.save(group);
    }

    @Override
    public void removeGroupRoles(Long groupId, GroupRoleRequest groupRoleRequest) {
        IpaAdminGroup group = getGroupById(groupId);
        List<IpaAdminRole> roleList = groupRoleRequest.groupRoleIds().stream()
                .map(roleService::getRoleById).toList();
        roleList.forEach(group::removeRole);
        groupRepository.save(group);
    }

    private void prepareEntity(GroupRequest groupRequest, IpaAdminGroup group) {
        group.setName(groupRequest.name());
        group.setActive(groupRequest.active());
    }

    private void prepareResponse(IpaAdminGroup group, GroupResponse groupResponse) {
        groupResponse.setId(group.getId());
        groupResponse.setName(group.getName());
        groupResponse.setActive(group.isActive());
    }
}
