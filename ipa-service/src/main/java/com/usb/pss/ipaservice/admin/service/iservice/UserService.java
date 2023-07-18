package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.request.ChangePasswordRequest;
import com.usb.pss.ipaservice.admin.dto.request.RegistrationRequest;
import com.usb.pss.ipaservice.admin.dto.request.UserActionRequest;
import com.usb.pss.ipaservice.admin.dto.request.UserRoleRequest;
import com.usb.pss.ipaservice.admin.dto.request.UserStatusRequest;
import com.usb.pss.ipaservice.admin.dto.response.MenuResponse;
import com.usb.pss.ipaservice.admin.dto.response.ModuleResponse;
import com.usb.pss.ipaservice.admin.dto.response.UserResponse;

import java.util.List;
import java.util.Set;

public interface UserService {

    void createNewUser(RegistrationRequest request);

    List<UserResponse> getAllUsers();

    Set<MenuResponse> getUserAllPermittedMenu();

    void addAdditionalAction(UserActionRequest userActionRequest);

//    Set<MenuResponse> getAllPermittedMenuByUser(User user);

    List<ModuleResponse> getModuleWiseUserActions(Long userId);

    void updateUserRole(UserRoleRequest userRoleRequest);

    void updateUserStatusInfo(UserStatusRequest request);

    void changeUserPassword(ChangePasswordRequest request);
}
