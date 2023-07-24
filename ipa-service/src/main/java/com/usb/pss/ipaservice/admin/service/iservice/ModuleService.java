package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.response.ModuleActionResponse;
import com.usb.pss.ipaservice.admin.dto.response.ModuleMenuResponse;
import com.usb.pss.ipaservice.admin.model.entity.User;

import java.util.List;

public interface ModuleService {
    List<ModuleActionResponse> getModuleWiseActions();

    List<ModuleMenuResponse> getModuleWithSubModulesAndMenus();

    List<ModuleActionResponse> getAllModulesByGroup(Long groupId);

    List<ModuleActionResponse> getModuleWiseUserActions(Long userId);

    List<ModuleMenuResponse> getModuleWiseUserMenu(User user);
}
