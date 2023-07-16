package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.response.ActionResponse;
import com.usb.pss.ipaservice.admin.dto.response.MenuResponse;
import com.usb.pss.ipaservice.admin.dto.response.ModuleResponse;
import com.usb.pss.ipaservice.admin.dto.response.SubModuleResponse;
import com.usb.pss.ipaservice.admin.model.entity.Action;
import com.usb.pss.ipaservice.admin.model.entity.Menu;
import com.usb.pss.ipaservice.admin.model.entity.Module;
import com.usb.pss.ipaservice.admin.model.entity.User;
import com.usb.pss.ipaservice.admin.repository.ModuleRepository;
import com.usb.pss.ipaservice.admin.repository.UserRepository;
import com.usb.pss.ipaservice.admin.service.iservice.ModuleService;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import com.usb.pss.ipaservice.common.ExceptionConstant;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;
    private final UserRepository userRepository;

    @Override
    public List<ModuleResponse> getModuleActions() {
        List<Module> modules = moduleRepository.findAllByParentModuleIsNull();
        return getModuleResponsesFromModules(modules);
    }

    @Override
    public List<ModuleResponse> getAllModulesByRole(Long roleId) {
        List<Module> modules = moduleRepository.getAllModulesForRole(roleId);
        return getModuleResponsesFromModules(modules);
    }

    @Override
    public List<ModuleResponse> getModuleWiseUserActions(Long userId) {
        User user = userRepository.findUserAndFetchActionById(userId).orElseThrow(() -> new ResourceNotFoundException(
            ExceptionConstant.USER_NOT_FOUND_BY_ID));

        Set<Action> roleWiseAction = user.getRole().getPermittedActions();

        List<Module> modules = moduleRepository.findAllModuleByUserId2(userId, roleWiseAction);
        return getModuleResponsesFromModules(modules);
    }

    @NotNull
    private List<ModuleResponse> getModuleResponsesFromModules(List<Module> modules) {
        return modules.stream().map(
                module -> getModuleResponseBuilder(module)
                    .subModules(
                        module.getSubModules()
                            .stream()
                            .map(
                                subModule -> getSubModuleResponseBuilder(subModule)
                                    .menus(
                                        subModule.getMenus()
                                            .stream().map(
                                                menu -> getMenuResponseBuilder(menu)
                                                    .actions(
                                                        menu.getActions()
                                                            .stream().map(
                                                                this::getActionResponseBuilder
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


    private ActionResponse getActionResponseBuilder(Action action) {
        return ActionResponse
            .builder()
            .id(action.getId())
            .name(action.getName())
            .description(action.getDescription())
            .build();
    }

    private MenuResponse.MenuResponseBuilder getMenuResponseBuilder(Menu menu) {
        return MenuResponse
            .builder()
            .id(menu.getId())
            .name(menu.getName())
            .description(menu.getDescription())
            .url(menu.getUrl())
            .sortOrder(menu.getSortOrder());
    }

    private SubModuleResponse.SubModuleResponseBuilder getSubModuleResponseBuilder(Module subModule) {
        return SubModuleResponse
            .builder()
            .id(subModule.getId())
            .name(String.valueOf(subModule.getName()))
            .description(subModule.getDescription())
            .sortOrder(subModule.getSortOrder());
    }

    private ModuleResponse.ModuleResponseBuilder getModuleResponseBuilder(Module module) {
        return ModuleResponse
            .builder()
            .id(module.getId())
            .name(String.valueOf(module.getName()))
            .description(module.getDescription())
            .sortOrder(module.getSortOrder());
    }
}
