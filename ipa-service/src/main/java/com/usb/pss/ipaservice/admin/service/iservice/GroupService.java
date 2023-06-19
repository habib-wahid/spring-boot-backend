package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.request.GroupRequest;
import com.usb.pss.ipaservice.admin.dto.request.GroupRoleRequest;
import com.usb.pss.ipaservice.admin.dto.response.GroupResponse;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminGroup;

import java.util.List;

/**
 * @author Junaid Khan Pathan
 * @date Jun 17, 2023
 */

public interface GroupService {
    void createNewGroup(GroupRequest groupRequest);
    IpaAdminGroup getGroupById(Long groupId);
    IpaAdminGroup getGroupByName(String groupName);
    GroupResponse getGroupResponseById(Long groupId);
    List<GroupResponse> getAllGroupResponse();
    void updateGroup(GroupRequest groupRequest, Long groupId);
    void deactivateGroup(Long groupId);
    void addGroupRoles(Long groupId, GroupRoleRequest groupRoleRequest);
    void removeGroupRoles(Long groupId, GroupRoleRequest groupRoleRequest);
}
