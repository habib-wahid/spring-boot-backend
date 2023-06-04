package com.usba.pss.auth.service;

import com.usba.pss.auth.model.RolesPermissions.GroupPermission;
import com.usba.pss.auth.model.RolesPermissions.Permission;
import com.usba.pss.utils.GenericResponse;
import com.usba.pss.utils.ResponseCode;
import com.usba.pss.auth.dto.groupRolePermission.GroupPermissionDto;
import com.usba.pss.auth.dto.groupRolePermission.PermissionDto;
import com.usba.pss.auth.dto.groupRolePermission.SaveGroupPermissionRequest;
import com.usba.pss.auth.dto.groupRolePermission.SaveSingleGroupPermissionsRequest;
import com.usba.pss.auth.model.RolesPermissions.Groups;
import com.usba.pss.auth.repository.GroupPermissionRepository;
import com.usba.pss.auth.repository.GroupRepository;
import com.usba.pss.auth.repository.PermissionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GroupPermissionService {
    private final GroupRepository groupRepository;
    private final PermissionRepository permissionRepository;
    private final GroupPermissionRepository groupPermissionRepository;

    @Transactional
    public GenericResponse saveGroupPermission(SaveGroupPermissionRequest request) {
        try {
            if (request != null && request.groupPermissionDtoList != null && request.groupPermissionDtoList.size() > 0) {
                for (GroupPermissionDto dto :
                        request.groupPermissionDtoList) {
                    GroupPermission groupPermission = new GroupPermission();

                    Optional<Permission> permissionEntity = permissionRepository.findById(dto.getPermissionId());
                    Optional<Groups> groupEntity = groupRepository.findById(dto.getGroupId());
                    if (groupEntity.isPresent() && permissionEntity.isPresent()) {
                        groupPermission = groupPermissionRepository.findByGroupsAndPermission(groupEntity.get(), permissionEntity.get());
                        if (groupPermission == null) {
                            groupPermission = new GroupPermission();
                            groupPermission.setGroups(groupEntity.get());
                            groupPermission.setPermission(permissionEntity.get());
                        }
                    } else {
                        return new GenericResponse(ResponseCode.DATA_NOT_FOUND.getCode(), "Wrong data");
                    }

                    groupPermission.setActive(dto.isActive());
                    groupPermissionRepository.save(groupPermission);
                }
                return new GenericResponse();
            } else {
                return new GenericResponse(ResponseCode.REQUIRED_PARAMETER_MISSING.getCode(), "Request Empty");
            }
        } catch (Exception ex) {
            return new GenericResponse(ResponseCode.SERVICE_ERROR.getCode(), "Service Error");
        }
    }

    public List<GroupPermissionDto> getGroupPermissions(Long groupId) {
        try {
            Set<GroupPermission> groupPermissionList = groupPermissionRepository.
                    findAllByGroupsAndActive(groupRepository.findById(groupId).get(), true);
            List<GroupPermissionDto> groupPermissionDtoList = new ArrayList<>();
            for (GroupPermission dto :
                    groupPermissionList) {
                GroupPermissionDto groupPermissionDto = new GroupPermissionDto();
                BeanUtils.copyProperties(dto, groupPermissionDto);
                groupPermissionDto.setGroupId(dto.getGroups().getId());
                groupPermissionDto.setPermissionId(dto.getPermission().getId());

                groupPermissionDtoList.add(groupPermissionDto);
            }
            return groupPermissionDtoList;
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }



    @Transactional
    public GenericResponse saveSingleGroupPermissions(SaveSingleGroupPermissionsRequest request) {
        try {
            if (request != null && request.groupId() != null &&
                    request.permissionList() != null && request.permissionList().size() > 0) {
                Optional<Groups> groupEntity = groupRepository.findById(request.groupId());
                for (PermissionDto dto :
                        request.permissionList()) {
                    GroupPermission groupPermission = new GroupPermission();
                    Optional<Permission> permissionEntity = permissionRepository.findById(dto.getId());
                    if (groupEntity.isPresent() && permissionEntity.isPresent()) {
                        groupPermission = groupPermissionRepository.findByGroupsAndPermission(groupEntity.get(), permissionEntity.get());
                        if (groupPermission == null) {
                            groupPermission = new GroupPermission();
                            groupPermission.setGroups(groupEntity.get());
                            groupPermission.setPermission(permissionEntity.get());
                        }
                    } else {
                        return new GenericResponse(ResponseCode.DATA_NOT_FOUND.getCode(), "Wrong data");
                    }

                    groupPermission.setActive(dto.isActive());
                    groupPermissionRepository.save(groupPermission);
                }
                return new GenericResponse();
            } else {
                return new GenericResponse(ResponseCode.REQUIRED_PARAMETER_MISSING.getCode(), "Request Empty");
            }
        } catch (Exception ex) {
            return new GenericResponse(ResponseCode.SERVICE_ERROR.getCode(), "Service Error");
        }
    }
}
