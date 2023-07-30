package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.request.ChangePasswordRequest;
import com.usb.pss.ipaservice.admin.dto.request.RegistrationRequest;
import com.usb.pss.ipaservice.admin.dto.request.UpdateUserInfoRequest;
import com.usb.pss.ipaservice.admin.dto.request.UserActionRequest;
import com.usb.pss.ipaservice.admin.dto.request.UserGroupRequest;
import com.usb.pss.ipaservice.admin.dto.request.UserStatusRequest;
import com.usb.pss.ipaservice.admin.dto.response.MenuActionResponse;
import com.usb.pss.ipaservice.admin.dto.response.ModuleActionResponse;
import com.usb.pss.ipaservice.admin.dto.response.UserPersonalInfoResponse;
import com.usb.pss.ipaservice.admin.dto.response.UserResponse;
import com.usb.pss.ipaservice.admin.model.entity.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    void createNewUser(RegistrationRequest request);
    User getUserById(Long userId);
    User getUserByUsername(String username);

    List<UserResponse> getAllUsers();

    Set<MenuActionResponse> getUserAllPermittedMenu();

    void addAdditionalAction(UserActionRequest userActionRequest);

//    Set<MenuResponse> getAllPermittedMenuByUser(User user);

    List<ModuleActionResponse> getModuleWiseUserActions(Long userId);

    void updateUserGroup(UserGroupRequest userGroupRequest);

    void updateUserStatusInfo(UserStatusRequest request);

    void changeUserPassword(ChangePasswordRequest request);

    void updateUserPersonalInfo(UpdateUserInfoRequest updateUserInfoRequest);

    UserPersonalInfoResponse getUserPersonalInfo(Long id);
}
