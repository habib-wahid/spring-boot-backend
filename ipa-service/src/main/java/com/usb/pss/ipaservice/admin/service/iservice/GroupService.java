package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.request.GroupActionRequest;
import com.usb.pss.ipaservice.admin.dto.request.GroupCreateRequest;
import com.usb.pss.ipaservice.admin.dto.request.GroupUpdateRequest;
import com.usb.pss.ipaservice.admin.dto.response.ModuleActionResponse;
import com.usb.pss.ipaservice.admin.dto.response.GroupResponse;
import com.usb.pss.ipaservice.admin.model.entity.Group;


import java.util.List;

/**
 * @author Junaid Khan Pathan
 * @date Jun 17, 2023
 */

public interface GroupService {
    void createNewGroup(GroupCreateRequest groupCreateRequest);

    GroupResponse getGroupById(Long groupId);

    GroupResponse getGroupResponse(Group group);

    Group findGroupById(Long groupId);

    List<GroupResponse> getAllGroupResponse();

    void updateGroup(GroupUpdateRequest groupUpdateRequest);

    void deactivateGroup(Long groupId);

    void updateGroupWiseAction(GroupActionRequest request);

    List<ModuleActionResponse> getGroupWisePermittedActions(Long groupId);

}
