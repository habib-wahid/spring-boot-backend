package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.request.ActionRequest;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminAction;

public interface ActionService {

    void saveUserAction(ActionRequest actionRequest);
    IpaAdminAction getUserActionById(Long actionId);
    String deleteUserActionById(Long actionId);
}
