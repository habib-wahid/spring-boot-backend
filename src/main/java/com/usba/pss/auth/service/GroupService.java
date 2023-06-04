package com.usba.pss.auth.service;

import com.usba.pss.auth.model.RolesPermissions.Groups;
import com.usba.pss.auth.model.RolesPermissions.UserGroup;
import com.usba.pss.auth.model.user.User;
import com.usba.pss.auth.repository.*;
import com.usba.pss.utils.GenericResponse;
import com.usba.pss.utils.ResponseCode;
import com.usba.pss.auth.dto.groupRolePermission.GroupsDto;
import com.usba.pss.auth.dto.groupRolePermission.SaveSingleUserGroupsRequest;
import com.usba.pss.auth.dto.groupRolePermission.SaveUserGroupRequest;
import com.usba.pss.auth.dto.groupRolePermission.UserGroupDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;
    private final GroupPermissionRepository groupPermissionRepository;

    public GenericResponse saveGroup(GroupsDto request) {
        Groups groups = new Groups();
        BeanUtils.copyProperties(request, groups);
        try {
            groupRepository.save(groups);
            return new GenericResponse();
        } catch (Exception ex) {
            return new GenericResponse(ResponseCode.SERVICE_ERROR.getCode(), "Service error");
        }
    }

    public List<GroupsDto> getGroups() {
        List<Groups> groupList = groupRepository.findAll();
        List<GroupsDto> groupDtoList = new ArrayList<>();
        for (Groups group :
                groupList) {
            GroupsDto dto = new GroupsDto();
            BeanUtils.copyProperties(group, dto);
            groupDtoList.add(dto);
        }
        return groupDtoList;
    }

    @Transactional
    public GenericResponse saveUserGroup(SaveUserGroupRequest request) {
        try {
            if (request != null && request.userGroupDtoList != null && request.userGroupDtoList.size() > 0) {
                for (UserGroupDto dto :
                        request.userGroupDtoList) {
                    UserGroup userGroup = new UserGroup();
                    Optional<User> userEntity = userRepository.findById(dto.getUserId());
                    Optional<Groups> groupEntity = groupRepository.findById(dto.getGroupId());
                    if (userEntity.isPresent() && groupEntity.isPresent()) {
                        userGroup = userGroupRepository.findByUserAndGroups(userEntity.get(), groupEntity.get());
                        if (userGroup == null) {
                            userGroup = new UserGroup();
                            userGroup.setUser(userEntity.get());
                            userGroup.setGroups(groupEntity.get());
                        }
                    } else {
                        return new GenericResponse(ResponseCode.DATA_NOT_FOUND.getCode(), "Wrong data");
                    }

                    userGroup.setActive(dto.isActive());

                    userGroupRepository.save(userGroup);
                }
                return new GenericResponse();
            } else {
                return new GenericResponse(ResponseCode.REQUIRED_PARAMETER_MISSING.getCode(), "Invalid Request");
            }
        } catch (
                Exception ex) {
            return new GenericResponse(ResponseCode.SERVICE_ERROR.getCode(), "Service Error");
        }
    }

    public List<UserGroupDto> getUserGroups(Long userId) {
        try {
            Set<UserGroup> userRoleList = userGroupRepository.findAllByUserAndActive(userRepository.findById(userId).get(), true);
            List<UserGroupDto> userGroupDtoList = new ArrayList<>();
            for (UserGroup dto :
                    userRoleList) {
                UserGroupDto userGroupDto = new UserGroupDto();
                BeanUtils.copyProperties(dto, userGroupDto);
                userGroupDto.setGroupId(dto.getGroups().getId());
                userGroupDto.setUserId(dto.getUser().getId());

                userGroupDtoList.add(userGroupDto);
            }
            return userGroupDtoList;
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }



    @Transactional
    public GenericResponse saveSingleUserGroups(SaveSingleUserGroupsRequest request) {
        try {
            if (request != null && request.userId() != null &&
                    request.groupList() != null && request.groupList().size() > 0) {
                Optional<User> userEntity = userRepository.findById(request.userId());
                for (GroupsDto dto :
                        request.groupList()) {
                    UserGroup userGroup = new UserGroup();
                    Optional<Groups> groupEntity = groupRepository.findById(dto.getId());
                    if (userEntity.isPresent() && groupEntity.isPresent()) {
                        userGroup = userGroupRepository.findByUserAndGroups(userEntity.get(), groupEntity.get());
                        if (userGroup == null) {
                            userGroup = new UserGroup();
                            userGroup.setUser(userEntity.get());
                            userGroup.setGroups(groupEntity.get());
                        }
                    } else {
                        return new GenericResponse(ResponseCode.DATA_NOT_FOUND.getCode(), "Wrong data");
                    }

                    userGroup.setActive(dto.isActive());

                    userGroupRepository.save(userGroup);
                }
                return new GenericResponse();
            } else {
                return new GenericResponse(ResponseCode.REQUIRED_PARAMETER_MISSING.getCode(), "Invalid Request");
            }
        } catch (
                Exception ex) {
            return new GenericResponse(ResponseCode.SERVICE_ERROR.getCode(), "Service Error");
        }
    }
}
