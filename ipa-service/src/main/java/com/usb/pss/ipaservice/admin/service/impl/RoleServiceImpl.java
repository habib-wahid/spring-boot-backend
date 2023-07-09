package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.request.RoleMenuRequest;
import com.usb.pss.ipaservice.admin.dto.request.RoleRequest;
import com.usb.pss.ipaservice.admin.dto.response.RoleResponse;
import com.usb.pss.ipaservice.admin.model.entity.Menu;
import com.usb.pss.ipaservice.admin.model.entity.Role;
import com.usb.pss.ipaservice.admin.repository.RoleRepository;
import com.usb.pss.ipaservice.admin.service.iservice.MenuService;
import com.usb.pss.ipaservice.admin.service.iservice.RoleService;
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
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final MenuService menuService;

    @Override
    public void createNewRole(RoleRequest roleRequest) {
        Optional<Role> duplicateRoleName = roleRepository.findByNameIgnoreCase(roleRequest.name());
        if (duplicateRoleName.isPresent()) {
            throw new RuleViolationException(ExceptionConstant.DUPLICATE_ROLE_NAME);
        }

        Role roleToSave = new Role();
        prepareEntity(roleRequest, roleToSave);
        roleRepository.save(roleToSave);
    }

    @Override
    public Role getRoleById(Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionConstant.ROLE_NOT_FOUND));
    }

    @Override
    public Role getRoleByName(String roleName) {
        return roleRepository.findByNameIgnoreCase(roleName)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionConstant.ROLE_NOT_FOUND));
    }

    @Override
    public RoleResponse getRoleResponseById(Long roleId) {
        Role role = getRoleById(roleId);
        RoleResponse roleResponse = new RoleResponse();
        prepareResponse(role, roleResponse);
        return roleResponse;
    }

    @Override
    public List<RoleResponse> getAllRoleResponse() {
        return roleRepository.findAllByOrderByCreatedDateDesc().stream()
                .map(role -> {
                    RoleResponse roleResponse = new RoleResponse();
                    prepareResponse(role, roleResponse);
                    return roleResponse;
                }).toList();
    }

    @Override
    public void updateRole(RoleRequest roleRequest, Long roleId) {
        Role roleToUpdate = getRoleById(roleId);
        if (!roleToUpdate.getName().equals(roleRequest.name())) {
            Optional<Role> duplicateRoleName = roleRepository.findByNameIgnoreCase(roleRequest.name());
            if (duplicateRoleName.isPresent()) {
                throw new RuleViolationException(ExceptionConstant.DUPLICATE_ROLE_NAME);
            }
        }

        prepareEntity(roleRequest, roleToUpdate);
        roleRepository.save(roleToUpdate);
    }

    @Override
    public void deactivateRole(Long roleId) {
        //TODO will be implemented later if needed
    }

    @Override
    public void addRoleMenu(Long roleId, RoleMenuRequest roleMenuRequest) {
        Role role = getRoleById(roleId);
        List<Menu> menuList = roleMenuRequest.roleMenuIds().stream()
                .map(menuService::getMenuById).toList();
        menuList.forEach(role::addMenu);
        roleRepository.save(role);
    }

    @Override
    public void removeRoleMenu(Long roleId, RoleMenuRequest roleMenuRequest) {
        Role role = getRoleById(roleId);
        List<Menu> menuList = roleMenuRequest.roleMenuIds().stream()
                .map(menuService::getMenuById).toList();
        menuList.forEach(role::removeMenu);
        roleRepository.save(role);
    }

    private void prepareEntity(RoleRequest roleRequest, Role role) {
        role.setName(roleRequest.name());
        role.setDescription(roleRequest.description());
    }

    private void prepareResponse(Role role, RoleResponse roleResponse) {
        roleResponse.setId(role.getId());
        roleResponse.setName(role.getName());
        roleResponse.setDescription(role.getDescription());
    }
}
