package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.request.ActionRequest;
import com.usb.pss.ipaservice.admin.dto.response.AdminActionResponse;
import com.usb.pss.ipaservice.admin.dto.response.ModuleResponse;
import com.usb.pss.ipaservice.admin.model.entity.Action;

import java.util.List;
import java.util.Set;

public interface ActionService {

    Set<Action> getAllActionByIds(List<Long> actionId);
    void saveUserAction(ActionRequest actionRequest);
    AdminActionResponse getUserActionById(Long actionId);
    String deleteUserActionById(Long actionId);
    List<ModuleResponse> getModuleActions();
}
