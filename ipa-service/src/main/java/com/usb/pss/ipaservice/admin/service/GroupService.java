package com.usb.pss.ipaservice.admin.service;

import com.usb.pss.ipaservice.admin.dto.request.GroupRequest;
import com.usb.pss.ipaservice.utils.GenericResponse;

public interface GroupService {
    GenericResponse createNewGroup(GroupRequest request);
}
