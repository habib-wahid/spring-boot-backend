package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.request.UserGroupRequest;
import com.usb.pss.ipaservice.admin.dto.request.RegistrationRequest;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminUser;

public interface UserService {

    void registerUser(RegistrationRequest request);

    IpaAdminUser getUserById(Long id);

    void updateGroup(UserGroupRequest userGroupRequest);

    void removeGroup(UserGroupRequest userGroupRequest);


}
