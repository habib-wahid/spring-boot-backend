package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.response.AdminActionResponse;

public interface ActionService {

    AdminActionResponse getUserActionById(Long actionId);

}
