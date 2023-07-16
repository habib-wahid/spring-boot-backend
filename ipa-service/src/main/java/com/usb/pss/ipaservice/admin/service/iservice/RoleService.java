package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.request.RoleActionRequest;
import com.usb.pss.ipaservice.admin.dto.request.RoleMenuRequest;
import com.usb.pss.ipaservice.admin.dto.request.RoleRequest;
import com.usb.pss.ipaservice.admin.dto.response.ModuleResponse;
import com.usb.pss.ipaservice.admin.dto.response.RoleResponse;
import com.usb.pss.ipaservice.admin.model.entity.Role;

import java.util.List;

/**
 * @author Junaid Khan Pathan
 * @date Jun 17, 2023
 */

public interface RoleService {
    void createNewRole(RoleRequest roleRequest);
    Role getRoleById(Long roleId);

    RoleResponse getRoleResponseById(Long roleId);
    List<RoleResponse> getAllRoleResponse();
    void updateRole(RoleRequest roleRequest, Long roleId);
    void deactivateRole(Long roleId);
    void addRoleMenu(Long roleId, RoleMenuRequest roleMenuRequest);
    void removeRoleMenu(Long roleId, RoleMenuRequest roleMenuRequest);
    void updateRoleAction(RoleActionRequest request);
    List<ModuleResponse> getRoleWisePermittedActions(Long roleId);
}
