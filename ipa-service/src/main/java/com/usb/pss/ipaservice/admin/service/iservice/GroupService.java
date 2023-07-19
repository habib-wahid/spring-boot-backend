package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.request.GroupActionRequest;
import com.usb.pss.ipaservice.admin.dto.request.GroupRequest;
import com.usb.pss.ipaservice.admin.dto.response.ModuleResponse;
import com.usb.pss.ipaservice.admin.dto.response.GroupResponse;
import com.usb.pss.ipaservice.admin.model.entity.Group;

import java.util.List;

/**
 * @author Junaid Khan Pathan
 * @date Jun 17, 2023
 */

public interface GroupService {
    void createNewGroup(GroupRequest groupRequest);

    Group getGroupById(Long groupId);

    GroupResponse getGroupResponseById(Long groupId);

    List<GroupResponse> getAllGroupResponse();

    void updateGroup(GroupRequest groupRequest, Long groupId);

    void deactivateGroup(Long groupId);

    void updateGroupAction(GroupActionRequest request);

    List<ModuleResponse> getGroupWisePermittedActions(Long groupId);
}
