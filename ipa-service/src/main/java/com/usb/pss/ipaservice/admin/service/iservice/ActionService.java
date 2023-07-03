package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.request.ActionRequest;
import com.usb.pss.ipaservice.admin.dto.response.AdminActionResponse;
import com.usb.pss.ipaservice.admin.dto.response.ModuleResponse;

import java.util.List;

public interface ActionService {

    void saveUserAction(ActionRequest actionRequest);
    AdminActionResponse getUserActionById(Long actionId);
    String deleteUserActionById(Long actionId);
    List<ModuleResponse> getModuleActions();
}
