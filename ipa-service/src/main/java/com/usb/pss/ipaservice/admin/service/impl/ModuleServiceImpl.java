package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.response.ActionResponse;
import com.usb.pss.ipaservice.admin.dto.response.MenuResponse;
import com.usb.pss.ipaservice.admin.dto.response.MenuResponseWithIdName;
import com.usb.pss.ipaservice.admin.dto.response.ModuleResponse;
import com.usb.pss.ipaservice.admin.dto.response.ModuleResponseWithSubModuleAndMenu;
import com.usb.pss.ipaservice.admin.dto.response.SubModuleResponse;
import com.usb.pss.ipaservice.admin.dto.response.SubModuleResponseWithMenuIdAndName;
import com.usb.pss.ipaservice.admin.model.entity.Action;
import com.usb.pss.ipaservice.admin.model.entity.Menu;
import com.usb.pss.ipaservice.admin.model.entity.Module;
import com.usb.pss.ipaservice.admin.model.entity.SubModule;
import com.usb.pss.ipaservice.admin.model.entity.User;
import com.usb.pss.ipaservice.admin.repository.ActionRepository;
import com.usb.pss.ipaservice.admin.repository.ModuleRepository;
import com.usb.pss.ipaservice.admin.repository.UserRepository;
import com.usb.pss.ipaservice.admin.service.iservice.ModuleService;
import com.usb.pss.ipaservice.common.ExceptionConstant;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;
    private final UserRepository userRepository;
    private final ActionRepository actionRepository;

    @Override
    public List<ModuleResponse> getModuleWiseActions() {
        List<Module> modules = moduleRepository.findAllByOrderBySortOrder();
        return getModuleResponsesFromModules(modules);
    }

    @Override
    public List<ModuleResponseWithSubModuleAndMenu> getModuleWithSubModulesAndMenus() {
        List<Module> modules = moduleRepository.findAllModulesWithSubModulesAndMenusByIdIsNotNull();
        return getModuleResponseWithSubModulesAndMenusFromModules(modules);
    }

    @Override
    public List<ModuleResponse> getAllModulesByGroup(Long groupId) {
        List<Module> modules = moduleRepository.findAllByOrderBySortOrder();
        Set<Action> permittedActions = new HashSet<>(actionRepository.findGroupWiseAction(groupId));
        return getModuleResponsesFromModules(modules, permittedActions);
    }

    @Override
    public List<ModuleResponse> getModuleWiseUserActions(Long userId) {
        User user = userRepository.findUserFetchAdditionalActionsById(userId)
            .orElseThrow(() -> new ResourceNotFoundException(
                ExceptionConstant.USER_NOT_FOUND_BY_ID));

        List<Module> modules = moduleRepository.findModuleWiseUserActions(
            user.getGroup(), user.getAdditionalActions()
        );
        return getModuleResponsesFromModules(modules);
    }

    private List<ModuleResponseWithSubModuleAndMenu> getModuleResponseWithSubModulesAndMenusFromModules(
        List<Module> modules) {
        return modules
            .stream()
            .map(module -> getModuleResponseWithSubModuleAndName(module)
                .subModules(
                    module.getSubModules()
                        .stream()
                        .map(subModule -> getSubModuleResponseWithMenuIdAndName(subModule)
                            .menus(
                                subModule.getMenus()
                                    .stream()
                                    .map(this::getMenuResponseWithIdAndName)
                                    .toList())
                            .build())
                        .sorted(Comparator.comparingInt(SubModuleResponseWithMenuIdAndName::getSortOrder))
                        .toList())
                .build())
            .sorted(Comparator.comparingInt(ModuleResponseWithSubModuleAndMenu::getSortOrder))
            .toList();
    }

    private List<ModuleResponse> getModuleResponsesFromModules(List<Module> modules) {
        return modules
            .stream()
            .map(module -> getModuleResponseBuilder(module)
                .subModules(
                    module.getSubModules()
                        .stream()
                        .map(subModule -> getSubModuleResponseBuilder(subModule)
                            .menus(
                                subModule.getMenus()
                                    .stream()
                                    .map(menu -> getMenuResponseBuilder(menu)
                                        .actions(
                                            menu.getActions()
                                                .stream()
                                                .map(this::getActionResponseBuilder)
                                                .sorted(Comparator.comparingInt(ActionResponse::getSortOrder))
                                                .toList()
                                        )
                                        .build())
                                    .sorted(Comparator.comparingInt(MenuResponse::getSortOrder))
                                    .toList())
                            .build())
                        .sorted(Comparator.comparingInt(SubModuleResponse::getSortOrder))
                        .toList())
                .build())
            .sorted(Comparator.comparingInt(ModuleResponse::getSortOrder))
            .toList();
    }

    private List<ModuleResponse> getModuleResponsesFromModules(List<Module> modules, Set<Action> permittedActions) {
        return modules
            .stream()
            .map(module -> getModuleResponseBuilder(module)
                .subModules(
                    module.getSubModules()
                        .stream()
                        .map(subModule -> getSubModuleResponseBuilder(subModule)
                            .menus(
                                subModule.getMenus()
                                    .stream()
                                    .map(menu -> getMenuResponseBuilder(menu)
                                        .actions(
                                            menu.getActions()
                                                .stream()
                                                .map(action -> getActionResponseBuilder(action, permittedActions))
                                                .sorted(Comparator.comparingInt(ActionResponse::getSortOrder))
                                                .toList()
                                        )
                                        .build())
                                    .sorted(Comparator.comparingInt(MenuResponse::getSortOrder))
                                    .toList())
                            .build())
                        .sorted(Comparator.comparingInt(SubModuleResponse::getSortOrder))
                        .toList())
                .build())
            .sorted(Comparator.comparingInt(ModuleResponse::getSortOrder))
            .toList();
    }


    private ActionResponse getActionResponseBuilder(Action action) {
        return ActionResponse
            .builder()
            .id(action.getId())
            .name(action.getName())
            .description(action.getDescription())
            .sortOrder(action.getSortOrder())
            .build();
    }

    private ActionResponse getActionResponseBuilder(Action action, Set<Action> permittedActions) {
        return ActionResponse
            .builder()
            .id(action.getId())
            .name(action.getName())
            .description(action.getDescription())
            .sortOrder(action.getSortOrder())
            .permitted(permittedActions.contains(action))
            .build();
    }

    private MenuResponseWithIdName getMenuResponseWithIdAndName(Menu menu) {
        return new MenuResponseWithIdName(menu.getId(), menu.getName());
    }

    private SubModuleResponseWithMenuIdAndName.SubModuleResponseWithMenuIdAndNameBuilder
    getSubModuleResponseWithMenuIdAndName(
        SubModule subModule) {
        return SubModuleResponseWithMenuIdAndName
            .builder()
            .id(subModule.getId())
            .name(String.valueOf(subModule.getName()))
            .description(subModule.getDescription())
            .sortOrder(subModule.getSortOrder());
    }

    private ModuleResponseWithSubModuleAndMenu.ModuleResponseWithSubModuleAndMenuBuilder
    getModuleResponseWithSubModuleAndName(
        Module module) {
        return ModuleResponseWithSubModuleAndMenu
            .builder()
            .id(module.getId())
            .name(String.valueOf(module.getName()))
            .description(module.getDescription())
            .sortOrder(module.getSortOrder());
    }

    private MenuResponse.MenuResponseBuilder getMenuResponseBuilder(Menu menu) {
        return MenuResponse
            .builder()
            .id(menu.getId())
            .name(menu.getName())
            .description(menu.getDescription())
            .screenId(menu.getScreenId())
            .url(menu.getUrl())
            .icon(menu.getIcon())
            .sortOrder(menu.getSortOrder());
    }

    private SubModuleResponse.SubModuleResponseBuilder getSubModuleResponseBuilder(SubModule subModule) {
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
