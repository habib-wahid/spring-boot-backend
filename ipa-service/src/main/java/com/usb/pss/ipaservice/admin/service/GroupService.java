package com.usb.pss.ipaservice.admin.service;

import com.usb.pss.ipaservice.admin.dto.GroupsDto;
import com.usb.pss.ipaservice.admin.dto.SaveSingleUserGroupsRequest;
import com.usb.pss.ipaservice.admin.dto.SaveUserGroupRequest;
import com.usb.pss.ipaservice.admin.dto.UserGroupDto;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminGroup;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminUser;
import com.usb.pss.ipaservice.admin.repository.IpaAdminGroupRepository;
import com.usb.pss.ipaservice.admin.repository.IpaAdminUserRepository;
import com.usb.pss.ipaservice.utils.GenericResponse;
import com.usb.pss.ipaservice.utils.ResponseCode;
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
    private final IpaAdminGroupRepository groupRepository;
    private final IpaAdminUserRepository userRepository;
    private final UserGroupRepository userGroupRepository;

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
                    IpaAdminGroup ipaAdminGroup;
                    Optional<IpaAdminUser> userEntity = userRepository.findById(dto.getUserId());
                    Optional<Groups> groupEntity = groupRepository.findById(dto.getGroupId());
                    if (userEntity.isPresent() && groupEntity.isPresent()) {
                        ipaAdminGroup = userGroupRepository.findByUserAndGroups(userEntity.get(), groupEntity.get());
                        if (ipaAdminGroup == null) {
                            ipaAdminGroup = new IpaAdminGroup();
                            ipaAdminGroup.setUser(userEntity.get());
                            ipaAdminGroup.setGroups(groupEntity.get());
                        }
                    } else {
                        return new GenericResponse(ResponseCode.DATA_NOT_FOUND.getCode(), "Wrong data");
                    }

                    ipaAdminGroup.setActive(dto.isActive());

                    userGroupRepository.save(ipaAdminGroup);
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
            Set<IpaAdminGroup> userRoleList = userGroupRepository.findAllByUserAndActive(userRepository.findById(userId).get(), true);
            List<UserGroupDto> userGroupDtoList = new ArrayList<>();
            for (IpaAdminGroup dto :
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
                Optional<IpaAdminUser> userEntity = userRepository.findById(request.userId());
                for (GroupsDto dto :
                        request.groupList()) {
                    IpaAdminGroup ipaAdminGroup = new IpaAdminGroup();
                    Optional<Groups> groupEntity = groupRepository.findById(dto.getId());
                    if (userEntity.isPresent() && groupEntity.isPresent()) {
                        ipaAdminGroup = userGroupRepository.findByUserAndGroups(userEntity.get(), groupEntity.get());
                        if (ipaAdminGroup == null) {
                            ipaAdminGroup = new IpaAdminGroup();
                            ipaAdminGroup.setUser(userEntity.get());
                            ipaAdminGroup.setGroups(groupEntity.get());
                        }
                    } else {
                        return new GenericResponse(ResponseCode.DATA_NOT_FOUND.getCode(), "Wrong data");
                    }

                    ipaAdminGroup.setActive(dto.isActive());

                    userGroupRepository.save(ipaAdminGroup);
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
