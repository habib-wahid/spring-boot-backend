package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.request.RoleMenuRequest;
import com.usb.pss.ipaservice.admin.dto.request.RoleRequest;
import com.usb.pss.ipaservice.admin.dto.response.RoleResponse;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminRole;

import java.util.List;

/**
 * @author Junaid Khan Pathan
 * @date Jun 17, 2023
 */

public interface RoleService {
    void createNewRole(RoleRequest roleRequest);
    IpaAdminRole getRoleById(Long roleId);
    IpaAdminRole getRoleByName(String roleName);
    RoleResponse getRoleResponseById(Long roleId);
    List<RoleResponse> getAllActiveRoles();
    List<RoleResponse> getAllInactiveRoles();
    void updateRole(RoleRequest roleRequest, Long roleId);
    void deactivateRole(Long roleId);
    void addRoleMenu(Long roleId, RoleMenuRequest roleMenuRequest);
    void removeRoleMenu(Long roleId, RoleMenuRequest roleMenuRequest);
}
