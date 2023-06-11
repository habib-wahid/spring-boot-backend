package com.usb.pss.ipaservice.admin.service;

import com.usb.pss.ipaservice.admin.dto.RoleDto;
import com.usb.pss.ipaservice.admin.dto.SaveSingleUserRolesRequest;
import com.usb.pss.ipaservice.admin.dto.SaveUserRoleRequest;
import com.usb.pss.ipaservice.admin.dto.UserRoleDto;
import com.usb.pss.ipaservice.admin.model.entity.Role;
import com.usb.pss.ipaservice.admin.model.entity.UserRole;
import com.usb.pss.ipaservice.admin.model.entity.User;
import com.usb.pss.ipaservice.admin.repository.RoleRepository;
import com.usb.pss.ipaservice.admin.repository.UserRepository;
import com.usb.pss.ipaservice.admin.repository.UserRoleRepository;
import com.usb.pss.ipaservice.utils.GenericResponse;
import com.usb.pss.ipaservice.utils.ResponseCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public GenericResponse saveRole(RoleDto request) {
        Role role = new Role();
        BeanUtils.copyProperties(request, role);
        try {
            roleRepository.save(role);
            return new GenericResponse();
        } catch (Exception ex) {
            return new GenericResponse(ResponseCode.SERVICE_ERROR.getCode(), "Service error");
        }
    }

    public List<RoleDto> getRoles() {
        List<Role> roleList = roleRepository.findAll();
        List<RoleDto> roleDtoList = new ArrayList<>();
        for (Role role :
                roleList) {
            RoleDto dto = new RoleDto();
            BeanUtils.copyProperties(role, dto);
            roleDtoList.add(dto);
        }
        return roleDtoList;
    }

    @Transactional
    public GenericResponse saveUserRole(SaveUserRoleRequest request) {
        try {
            if (request != null && request.userRoleDtoList != null && request.userRoleDtoList.size() > 0) {
                    for (UserRoleDto dto :
                            request.userRoleDtoList) {
                        UserRole userRole;
                        Optional<Role> roleEntity = roleRepository.findById(dto.getRoleId());
                        Optional<User> userEntity = userRepository.findById(dto.getUserId());
                        if (userEntity.isPresent() && roleEntity.isPresent()) {
                            userRole = userRoleRepository.findByUserAndRole(userEntity.get(), roleEntity.get());
                            if (userRole == null) {
                                userRole = new UserRole();
                                userRole.setUser(userEntity.get());
                                userRole.setRole(roleEntity.get());
                            }
                        } else {
                            return new GenericResponse(ResponseCode.DATA_NOT_FOUND.getCode(), "Wrong data");
                        }

                        userRole.setActive(dto.isActive());
                        userRoleRepository.save(userRole);
                    }
                    return new GenericResponse();
            } else {
                return new GenericResponse(ResponseCode.REQUIRED_PARAMETER_MISSING.getCode(), "Invalid Request");
            }
        } catch (Exception ex) {
            return new GenericResponse(ResponseCode.SERVICE_ERROR.getCode(), "Service Error");
        }
    }

    public List<UserRoleDto> getUserRoles(Long userId) {
        try {
            Set<UserRole> userRoleList = userRoleRepository.findAllByUserAndActive(userRepository.findById(userId).get(), true);
            List<UserRoleDto> userRoleDtoList = new ArrayList<>();
            for (UserRole dto:
                 userRoleList) {
                UserRoleDto userRoleDto = new UserRoleDto();
                BeanUtils.copyProperties(dto, userRoleDto);
                userRoleDto.setRoleId(dto.getRole().getId());
                userRoleDto.setUserId(dto.getUser().getId());

                userRoleDtoList.add(userRoleDto);
            }
            return userRoleDtoList;
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }


    @Transactional
    public GenericResponse saveSingleUserRoles(SaveSingleUserRolesRequest request) {
        try {
            if (request != null && request.userId() != null && request.roleList() != null && request.roleList().size() > 0) {
                Optional<User> userEntity = userRepository.findById(request.userId());
                for (RoleDto dto :
                        request.roleList()) {
                    UserRole userRole;
                    Optional<Role> roleEntity = roleRepository.findById(dto.getId());
                    if (userEntity.isPresent() && roleEntity.isPresent()) {
                        userRole = userRoleRepository.findByUserAndRole(userEntity.get(), roleEntity.get());
                        if (userRole == null) {
                            userRole = new UserRole();
                            userRole.setUser(userEntity.get());
                            userRole.setRole(roleEntity.get());
                        }
                    } else {
                        return new GenericResponse(ResponseCode.DATA_NOT_FOUND.getCode(), "Wrong data");
                    }

                    userRole.setActive(dto.isActive());
                    userRoleRepository.save(userRole);
                }
                return new GenericResponse();
            } else {
                return new GenericResponse(ResponseCode.REQUIRED_PARAMETER_MISSING.getCode(), "Invalid Request");
            }
        } catch (Exception ex) {
            return new GenericResponse(ResponseCode.SERVICE_ERROR.getCode(), "Service Error");
        }
    }
}
