package com.usba.pss.auth.service;

import com.usba.pss.auth.model.RolesPermissions.Permission;
import com.usba.pss.auth.model.RolesPermissions.Role;
import com.usba.pss.auth.model.RolesPermissions.RolePermission;
import com.usba.pss.utils.GenericResponse;
import com.usba.pss.utils.ResponseCode;
import com.usba.pss.auth.dto.groupRolePermission.PermissionDto;
import com.usba.pss.auth.dto.groupRolePermission.RolePermissionDto;
import com.usba.pss.auth.dto.groupRolePermission.SaveRolePermissionRequest;
import com.usba.pss.auth.dto.groupRolePermission.SaveSingleRolePermissionsRequest;
import com.usba.pss.auth.repository.PermissionRepository;
import com.usba.pss.auth.repository.RolePermissionRepository;
import com.usba.pss.auth.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RolePermissionService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;

    @Transactional
    public GenericResponse saveRolePermission(SaveRolePermissionRequest request) {
        try {
            if (request != null && request.rolePermissionDtoList != null && request.rolePermissionDtoList.size() > 0) {
                for (RolePermissionDto dto :
                        request.rolePermissionDtoList) {
                    RolePermission rolePermission = new RolePermission();

                    Optional<Role> roleEntity = roleRepository.findById(dto.getRoleId());
                    Optional<Permission> permissionEntity = permissionRepository.findById(dto.getPermissionId());
                    if (roleEntity.isPresent() && permissionEntity.isPresent()) {
                        rolePermission = rolePermissionRepository.findByRoleAndPermission(roleEntity.get(), permissionEntity.get());
                        if (rolePermission == null) {
                            rolePermission = new RolePermission();
                            rolePermission.setRole(roleEntity.get());
                            rolePermission.setPermission(permissionEntity.get());
                        }
                    } else {
                        return new GenericResponse(ResponseCode.DATA_NOT_FOUND.getCode(), "Wrong role id");
                    }

                    rolePermission.setActive(dto.isActive());
                    rolePermissionRepository.save(rolePermission);
                }
                return new GenericResponse();
            } else {
                return new GenericResponse(ResponseCode.REQUIRED_PARAMETER_MISSING.getCode(), "Request Empty");
            }
        } catch (Exception ex) {
            return new GenericResponse(ResponseCode.SERVICE_ERROR.getCode(), "Service Error");
        }
    }

    public List<RolePermissionDto> getRolePermissions(Long roleId) {
        try {
            Set<RolePermission> rolePermissionList = rolePermissionRepository.
                    findAllByRoleAndActive(roleRepository.findById(roleId).get(), true);
            List<RolePermissionDto> rolePermissionDtoList = new ArrayList<>();
            for (RolePermission dto :
                    rolePermissionList) {
                RolePermissionDto userRoleDto = new RolePermissionDto();
                BeanUtils.copyProperties(dto, userRoleDto);
                userRoleDto.setRoleId(dto.getRole().getId());
                userRoleDto.setPermissionId(dto.getPermission().getId());

                rolePermissionDtoList.add(userRoleDto);
            }
            return rolePermissionDtoList;
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    @Transactional
    public GenericResponse saveSingleRolePermissions(SaveSingleRolePermissionsRequest request) {
        try {
            if (request != null && request.roleId() != null &&
                    request.permissionList() != null && request.permissionList().size() > 0) {
                Optional<Role> roleEntity = roleRepository.findById(request.roleId());
                for (PermissionDto dto :
                        request.permissionList()) {
                    RolePermission rolePermission = new RolePermission();

                    Optional<Permission> permissionEntity = permissionRepository.findById(dto.getId());
                    if (roleEntity.isPresent() && permissionEntity.isPresent()) {
                        rolePermission = rolePermissionRepository.findByRoleAndPermission(roleEntity.get(), permissionEntity.get());
                        if (rolePermission == null) {
                            rolePermission = new RolePermission();
                            rolePermission.setRole(roleEntity.get());
                            rolePermission.setPermission(permissionEntity.get());
                        }
                    } else {
                        return new GenericResponse(ResponseCode.DATA_NOT_FOUND.getCode(), "Wrong role id");
                    }

                    rolePermission.setActive(dto.isActive());
                    rolePermissionRepository.save(rolePermission);
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
