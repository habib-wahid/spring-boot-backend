package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.request.UserGroupRequest;
import com.usb.pss.ipaservice.admin.dto.request.RegistrationRequest;
import com.usb.pss.ipaservice.admin.dto.request.UserMenuRequest;
import com.usb.pss.ipaservice.admin.dto.response.MenuResponse;
import com.usb.pss.ipaservice.admin.dto.response.UserResponse;

import com.usb.pss.ipaservice.admin.model.entity.User;
import java.util.List;
import java.util.Set;

public interface UserService {

    void registerUser(RegistrationRequest request);

//    void updateGroup(UserGroupRequest userGroupRequest);
//
//    void removeGroup(UserGroupRequest userGroupRequest);

    List<UserResponse> getAllUsers();

    Set<MenuResponse> getUserAllPermittedMenu();

    void addUserMenus(Long userId, UserMenuRequest userMenuRequest);

    void removeUserMenus(Long userId, UserMenuRequest userMenuRequest);
    Set<MenuResponse> getAllPermittedMenuByUser(User user);

}
