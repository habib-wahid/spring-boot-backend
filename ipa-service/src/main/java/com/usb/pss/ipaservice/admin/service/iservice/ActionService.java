package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.request.ActionRequest;
import com.usb.pss.ipaservice.admin.dto.response.AdminActionResponse;
import com.usb.pss.ipaservice.admin.model.entity.Action;

import java.util.List;
import java.util.Set;

public interface ActionService {

    List<Action> getAllActionsByIdsWithMenu(Set<Long> actionId);

    void saveUserAction(ActionRequest actionRequest);

    AdminActionResponse getUserActionById(Long actionId);

    String deleteUserActionById(Long actionId);


}
