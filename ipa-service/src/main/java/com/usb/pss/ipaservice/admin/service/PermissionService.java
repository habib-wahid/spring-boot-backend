package com.usb.pss.ipaservice.admin.service;

import com.usb.pss.ipaservice.admin.dto.PermissionDto;
import com.usb.pss.ipaservice.admin.dto.RolePermissionDto;
import com.usb.pss.ipaservice.admin.dto.UserGroupDto;
import com.usb.pss.ipaservice.admin.dto.UserRoleDto;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminMenu;
import com.usb.pss.ipaservice.admin.repository.IpaAdminActionRepository;
import com.usb.pss.ipaservice.utils.GenericResponse;
import com.usb.pss.ipaservice.utils.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final IpaAdminActionRepository permissionRepository;
    private final RoleService roleService;
    private final GroupService groupService;
    private final RolePermissionService rolePermissionService;
    private final GroupPermissionService groupPermissionService;

    public GenericResponse savePermission(PermissionDto request) {
        IpaAdminMenu permission = new IpaAdminMenu();
        BeanUtils.copyProperties(request, permission);

        try {
            if (request.getParentId() != null) {
                if (!permissionRepository.findById(request.getParentId()).isPresent()) {
                    return new GenericResponse(ResponseCode.DATA_NOT_FOUND.getCode(), "No parent menu found");
                }
                permission.setParentId(permissionRepository.findById(request.getParentId()).get());
            }
            permissionRepository.save(permission);

            return new GenericResponse();
        } catch (Exception ex) {
            return new GenericResponse(ResponseCode.SERVICE_ERROR.getCode(), "Service error");
        }
    }

    public List<PermissionDto> getPermissions() {
        List<IpaAdminMenu> permissionList = permissionRepository.findAll();
        List<PermissionDto> permissionDtoList = new ArrayList<>();
        for (IpaAdminMenu permission :
                permissionList) {
            PermissionDto dto = new PermissionDto();
            BeanUtils.copyProperties(permission, dto);
            permissionDtoList.add(dto);
        }
        return permissionDtoList;
    }

    public List<PermissionDto> getUserPermissions(Long userId) {
        try {
            List<PermissionDto> permissionDtoList = new ArrayList<>();
            Map<Long, PermissionDto> permissionMap = new HashMap<>();
            List<UserRoleDto> userRoleDtoList = roleService.getUserRoles(userId);
            for (UserRoleDto userRoleDto :
                    userRoleDtoList) {
                List<RolePermissionDto> rolePermissionDtoList = rolePermissionService.getRolePermissions(userRoleDto.getRoleId());
                for (RolePermissionDto rolePermissionDto :
                        rolePermissionDtoList) {
                    if (!permissionMap.containsKey(rolePermissionDto.getPermissionId())) {
                        PermissionDto permissionDto = new PermissionDto();
                        IpaAdminMenu permission = permissionRepository.findById(rolePermissionDto.getPermissionId()).get();

                        BeanUtils.copyProperties(permission, permissionDto);
                        if (permission.getParentId() != null && permission.getParentId().getId() != null) {
                            permissionDto.setParentId(permission.getParentId().getId());
                        }
                        permissionMap.put(permissionDto.getId(), permissionDto);
                        permissionDtoList.add(permissionDto);
                    }
                }
            }

            List<UserGroupDto> userGroupDtoList = groupService.getUserGroups(userId);
            for (UserGroupDto userGroupDto :
                    userGroupDtoList) {
                List<GroupPermissionDto> groupPermissionDtoList = groupPermissionService.getGroupPermissions(userGroupDto.getGroupId());
                for (GroupPermissionDto groupPermissionDto :
                        groupPermissionDtoList) {

                    if (!permissionMap.containsKey(groupPermissionDto.getPermissionId())) {

                        PermissionDto permissionDto = new PermissionDto();
                        IpaAdminMenu permission = permissionRepository.findById(groupPermissionDto.getPermissionId()).get();

                        BeanUtils.copyProperties(permission, permissionDto);
                        if (permission.getParentId() != null && permission.getParentId().getId() != null) {
                            permissionDto.setParentId(permission.getParentId().getId());
                        }
                        permissionMap.put(permissionDto.getId(), permissionDto);
                        permissionDtoList.add(permissionDto);
                    }
                }
            }

            return permissionDtoList;
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }
}
