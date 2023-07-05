package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.response.ActionResponse;
import com.usb.pss.ipaservice.admin.dto.response.MenuResponse;
import com.usb.pss.ipaservice.admin.dto.response.ModuleResponse;
import com.usb.pss.ipaservice.admin.dto.response.SubModuleResponse;
import com.usb.pss.ipaservice.admin.model.entity.Module;
import com.usb.pss.ipaservice.admin.repository.ModuleRepository;
import com.usb.pss.ipaservice.admin.service.iservice.ModuleService;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;

    @Override
    public List<ModuleResponse> getModuleActions() {
        List<Module> modules = moduleRepository.findAllByParentModuleIsNull();
        return getModuleActionsByModules(modules);
    }

    @Override
    public List<ModuleResponse> getModuleActionsByModules(List<Module> modules) {
        return modules.stream().map(
                module -> ModuleResponse
                    .builder()
                    .id(module.getId())
                    .name(String.valueOf(module.getName()))
                    .description(module.getDescription())
                    .sortOrder(module.getSortOrder())
                    .subModules(
                        module.getSubModules()
                            .stream()
                            .map(
                                subModule -> SubModuleResponse
                                    .builder()
                                    .id(subModule.getId())
                                    .name(String.valueOf(subModule.getName()))
                                    .description(subModule.getDescription())
                                    .sortOrder(subModule.getSortOrder())
                                    .menus(
                                        subModule.getMenus()
                                            .stream().map(
                                                menu -> MenuResponse
                                                    .builder()
                                                    .id(menu.getId())
                                                    .name(menu.getName())
                                                    .description(menu.getDescription())
                                                    .url(menu.getUrl())
                                                    .sortOrder(menu.getSortOrder())
                                                    .actions(
                                                        menu.getActions()
                                                            .stream().map(
                                                                action -> ActionResponse
                                                                    .builder()
                                                                    .id(action.getId())
                                                                    .name(action.getName())
                                                                    .description(action.getDescription())
                                                                    .build()
                                                            ).toList()
                                                    )
                                                    .build()
                                            ).sorted(Comparator.comparingInt(MenuResponse::getSortOrder))
                                            .toList()
                                    )
                                    .build()
                            ).sorted(Comparator.comparingInt(SubModuleResponse::getSortOrder))
                            .toList()
                    )
                    .build()
            ).sorted(Comparator.comparingInt(ModuleResponse::getSortOrder))
            .toList();
    }

    @Override
    public List<Module> getAllModulesByRole(Long roleId) {
        return moduleRepository.getAllModulesForRole(roleId);
    }
}
