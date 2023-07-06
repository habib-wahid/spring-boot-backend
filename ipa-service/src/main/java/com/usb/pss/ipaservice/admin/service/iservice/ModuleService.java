package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.response.ModuleResponse;
import java.util.List;

public interface ModuleService {
    List<ModuleResponse> getModuleActions();
    List<ModuleResponse> getAllModulesByRole(Long roleId);
    List<ModuleResponse> getModuleWiseUserActions(Long userId);
}
