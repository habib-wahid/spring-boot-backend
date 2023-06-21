package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.request.UserGroupRequest;
import com.usb.pss.ipaservice.admin.dto.request.RegistrationRequest;
import com.usb.pss.ipaservice.admin.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    void registerUser(RegistrationRequest request);

    void updateGroup(UserGroupRequest userGroupRequest);

    void removeGroup(UserGroupRequest userGroupRequest);

    List<UserResponse> getAllUsers();


}
