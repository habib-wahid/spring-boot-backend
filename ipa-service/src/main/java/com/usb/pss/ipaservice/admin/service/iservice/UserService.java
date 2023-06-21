package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.request.UserGroupRequest;
import com.usb.pss.ipaservice.admin.dto.request.RegistrationRequest;

public interface UserService {

    void registerUser(RegistrationRequest request);

    void updateGroup(UserGroupRequest userGroupRequest);

    void removeGroup(UserGroupRequest userGroupRequest);


}
