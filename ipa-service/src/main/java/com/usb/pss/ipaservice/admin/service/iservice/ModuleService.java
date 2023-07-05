package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.response.ModuleResponse;
import com.usb.pss.ipaservice.admin.model.entity.Module;
import java.util.List;

public interface ModuleService {
    List<ModuleResponse> getModuleActions();
    List<ModuleResponse> getModuleActionsByModules(List<Module> modules);
    List<Module> getAllModulesByRole(Long roleId);
}
