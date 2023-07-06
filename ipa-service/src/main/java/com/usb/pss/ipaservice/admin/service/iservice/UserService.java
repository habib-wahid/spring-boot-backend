package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.request.RegistrationRequest;
import com.usb.pss.ipaservice.admin.dto.request.UserActionRequest;
import com.usb.pss.ipaservice.admin.dto.response.MenuResponse;
import com.usb.pss.ipaservice.admin.dto.response.ModuleResponse;
import com.usb.pss.ipaservice.admin.dto.response.UserResponse;

import com.usb.pss.ipaservice.admin.model.entity.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    void registerUser(RegistrationRequest request);

    List<UserResponse> getAllUsers();

    Set<MenuResponse> getUserAllPermittedMenu();

    void updateUserActions(UserActionRequest userActionRequest);

    Set<MenuResponse> getAllPermittedMenuByUser(User user);

    List<ModuleResponse> getModuleWiseUserActions(Long userId);
}
