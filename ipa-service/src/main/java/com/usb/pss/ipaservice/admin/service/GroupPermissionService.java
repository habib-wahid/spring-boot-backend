package com.usb.pss.ipaservice.admin.service;

import com.usb.pss.ipaservice.admin.dto.GroupPermissionDto;
import com.usb.pss.ipaservice.admin.dto.PermissionDto;
import com.usb.pss.ipaservice.admin.dto.SaveGroupPermissionRequest;
import com.usb.pss.ipaservice.admin.dto.SaveSingleGroupPermissionsRequest;
import com.usb.pss.ipaservice.admin.model.entity.GroupPermission;
import com.usb.pss.ipaservice.admin.model.entity.Groups;
import com.usb.pss.ipaservice.admin.model.entity.Permission;
import com.usb.pss.ipaservice.admin.repository.GroupPermissionRepository;
import com.usb.pss.ipaservice.admin.repository.GroupRepository;
import com.usb.pss.ipaservice.admin.repository.PermissionRepository;
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
                    GroupPermission groupPermission;

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
                    GroupPermission groupPermission;
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
