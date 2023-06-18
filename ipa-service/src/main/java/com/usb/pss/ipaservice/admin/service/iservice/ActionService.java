package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.request.ActionRequest;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminAction;

public interface ActionService {

    IpaAdminAction saveUserAction(ActionRequest actionRequest);
    IpaAdminAction getUserAction(Long actionId);
    String deleteUserAction(Long actionId);
}
