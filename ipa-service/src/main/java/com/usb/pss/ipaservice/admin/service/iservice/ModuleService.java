package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.response.ModuleResponse;
import com.usb.pss.ipaservice.admin.dto.response.ModuleResponseWithSubModuleAndMenu;

import java.util.List;

public interface ModuleService {
    List<ModuleResponse> getModuleActions();

    List<ModuleResponseWithSubModuleAndMenu> getModuleWithSubModulesAndMenus();
    List<ModuleResponse> getAllModulesByRole(Long roleId);
    List<ModuleResponse> getModuleWiseUserActions(Long userId);
}
