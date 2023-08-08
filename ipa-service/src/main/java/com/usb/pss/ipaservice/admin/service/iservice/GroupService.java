package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.PaginationResponse;
import com.usb.pss.ipaservice.admin.dto.request.GroupActionRequest;
import com.usb.pss.ipaservice.admin.dto.request.GroupActivationRequest;
import com.usb.pss.ipaservice.admin.dto.request.GroupCreateRequest;
import com.usb.pss.ipaservice.admin.dto.request.GroupSearchRequest;
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

    PaginationResponse<GroupResponse> getAllGroupResponse(int pageNumber, int pageSize);

    void updateGroup(GroupUpdateRequest groupUpdateRequest);

    void updateGroupActivationStatus(GroupActivationRequest request);

    void updateGroupWiseAction(GroupActionRequest request);

    List<ModuleActionResponse> getGroupWisePermittedActions(Long groupId);

    PaginationResponse<GroupResponse> getGroupsBySearchCriteria(GroupSearchRequest request, int page, int pageSize);

}
