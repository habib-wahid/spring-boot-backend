package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.request.AdditionalActionPermissionRequest;
import com.usb.pss.ipaservice.admin.dto.response.ActionResponse;
import com.usb.pss.ipaservice.admin.dto.response.MenuActionResponse;
import com.usb.pss.ipaservice.admin.dto.response.MenuResponse;
import com.usb.pss.ipaservice.admin.dto.response.ModuleActionResponse;
import com.usb.pss.ipaservice.admin.dto.response.ModuleMenuResponse;
import com.usb.pss.ipaservice.admin.dto.response.SubModuleActionResponse;
import com.usb.pss.ipaservice.admin.dto.response.SubModuleMenuResponse;
import com.usb.pss.ipaservice.admin.dto.response.ModuleResponse;
import com.usb.pss.ipaservice.admin.model.entity.Action;
import com.usb.pss.ipaservice.admin.model.entity.Menu;
import com.usb.pss.ipaservice.admin.model.entity.Module;
import com.usb.pss.ipaservice.admin.model.entity.SubModule;
import com.usb.pss.ipaservice.admin.model.entity.User;
import com.usb.pss.ipaservice.admin.repository.ActionRepository;
import com.usb.pss.ipaservice.admin.repository.ModuleRepository;
import com.usb.pss.ipaservice.admin.repository.UserRepository;
import com.usb.pss.ipaservice.admin.service.iservice.ModuleService;
import com.usb.pss.ipaservice.common.constants.ExceptionConstant;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import lombok.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Comparator;
import java.util.Set;
import java.util.HashSet;


@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;
    private final UserRepository userRepository;
    private final ActionRepository actionRepository;
   // private final UserServiceImpl userService;

    @Override
    public List<ModuleActionResponse> getModuleWiseActions() {
        List<Module> modules = moduleRepository.findAllByOrderBySortOrder();
        return getModuleResponsesFromModules(modules);
    }

    @Override
    public List<ModuleMenuResponse> getModuleWithSubModulesAndMenus() {
        List<Module> modules = moduleRepository.findAllModulesWithSubModulesAndMenusByIdIsNotNull();
        return getModuleResponseWithSubModulesAndMenus(modules);
    }

    @Override
    public List<ModuleActionResponse> getAllModulesByGroup(Long groupId) {
        List<Module> modules = moduleRepository.findAll();
        Set<Action> permittedActions = new HashSet<>(actionRepository.findGroupWiseAction(groupId));
        return getModuleResponsesFromModules(modules, permittedActions);
    }

    @Override
    public List<ModuleActionResponse> getModuleWiseUserActions(Long userId) {
        User user = userRepository.findUserFetchAdditionalActionsById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ExceptionConstant.USER_NOT_FOUND_BY_ID));

        List<Module> modules = moduleRepository.findModuleWiseUserActions(
                user.getGroup(), user.getAdditionalActions()
        );
        return getModuleResponsesFromModules(modules);
    }

    @Override
    public List<ModuleActionResponse> getModuleWiseUserActions(User user) {
        List<Module> modules = moduleRepository.findModuleWiseUserActions(
                user.getGroup(), user.getAdditionalActions()
        );
        return getModuleResponsesFromModules(modules);
    }

    @Override
    public List<ModuleResponse> getAllModules() {
        List<Module> modules = moduleRepository.findAll(Sort.by("sortOrder").ascending());
        return modules.stream().map(module -> ModuleResponse.builder()
                        .id(module.getId())
                        .name(module.getName())
                        .description(module.getDescription())
                        .sortOrder(module.getSortOrder()).build()
                )
                .toList();
    }

    @Override
    public List<ModuleActionResponse> getAllAdditionalActionsWithModules(Long userId, Long moduleId) {
        User user = userRepository.findUserFetchAdditionalActionsAndGroupActionsById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ExceptionConstant.USER_NOT_FOUND_BY_ID));
        List<Module> modules = moduleRepository.findUserAdditionalActionPermission(
                user.getGroup().getPermittedActions(), moduleId
        );
        return getModuleResponsesFromModules(modules, user.getAdditionalActions());
    }

    @Override
    public void editAdditionalActions(AdditionalActionPermissionRequest additionalActionPermissionRequest) {
        User user = userRepository.findUserFetchAdditionalActionsById(additionalActionPermissionRequest.userId())
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionConstant.USER_NOT_FOUND_BY_ID));
        List<Action> actions = moduleRepository.
                findUserAdditionalActionsByModule(user.getAdditionalActions(), additionalActionPermissionRequest.moduleId());
       // UserDto userDto = userService.getUserDto(user);
        List<Action> newAssignedActions = actionRepository.findByIdIn(additionalActionPermissionRequest.actionIds());
        List<Action> actionsToBeDeleted = actions.stream().filter(action -> !newAssignedActions.contains(action)).toList();
        user.getAdditionalActions().removeAll(actionsToBeDeleted);
        user.getAdditionalActions().addAll(new HashSet<>(newAssignedActions));
        userRepository.save(user);
    }

    private List<ModuleMenuResponse> getModuleResponseWithSubModulesAndMenus(
            List<Module> modules
    ) {
        return modules
                .stream()
                .map(module -> getModuleMenuResponse(module)
                        .subModules(
                                module.getSubModules()
                                        .stream()
                                        .map(subModule -> getSubModuleMenuResponse(subModule)
                                                .menus(
                                                        subModule.getMenus()
                                                                .stream()
                                                                .map(this::getMenuResponse)
                                                                .sorted(Comparator.comparingInt(MenuResponse::getSortOrder))
                                                                .toList()
                                                )
                                                .build()
                                        )
                                        .sorted(Comparator.comparingInt(SubModuleMenuResponse::getSortOrder))
                                        .toList()
                        )
                        .build()
                )
                .sorted(Comparator.comparingInt(ModuleMenuResponse::getSortOrder))
                .toList();
    }

    private List<ModuleActionResponse> getModuleResponsesFromModules(List<Module> modules) {
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
                                                                .sorted(Comparator.comparingInt(MenuActionResponse::getSortOrder))
                                                                .toList())
                                                .build())
                                        .sorted(Comparator.comparingInt(SubModuleActionResponse::getSortOrder))
                                        .toList())
                        .build())
                .sorted(Comparator.comparingInt(ModuleActionResponse::getSortOrder))
                .toList();
    }

    private List<ModuleActionResponse> getModuleResponsesFromModules(
            List<Module> modules,
            Set<Action> permittedActions
    ) {
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
                                                                .sorted(Comparator.comparingInt(MenuActionResponse::getSortOrder))
                                                                .toList())
                                                .build())
                                        .sorted(Comparator.comparingInt(SubModuleActionResponse::getSortOrder))
                                        .toList())
                        .build())
                .sorted(Comparator.comparingInt(ModuleActionResponse::getSortOrder))
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

    private MenuResponse getMenuResponse(Menu menu) {
        return new MenuResponse(
                menu.getId(),
                menu.getName(),
                menu.getDescription(),
                menu.getScreenId(),
                menu.getUrl(),
                menu.getIcon(),
                menu.getSortOrder()
        );
    }

    private SubModuleMenuResponse.SubModuleMenuResponseBuilder getSubModuleMenuResponse(
            SubModule subModule) {
        return SubModuleMenuResponse
                .builder()
                .id(subModule.getId())
                .name(String.valueOf(subModule.getName()))
                .description(subModule.getDescription())
                .sortOrder(subModule.getSortOrder());
    }

    private ModuleMenuResponse.ModuleMenuResponseBuilder getModuleMenuResponse(
            Module module
    ) {
        return ModuleMenuResponse
                .builder()
                .id(module.getId())
                .name(String.valueOf(module.getName()))
                .description(module.getDescription())
                .sortOrder(module.getSortOrder());
    }

    private MenuActionResponse.MenuActionResponseBuilder getMenuResponseBuilder(Menu menu) {
        return MenuActionResponse
                .builder()
                .id(menu.getId())
                .name(menu.getName())
                .description(menu.getDescription())
                .screenId(menu.getScreenId())
                .url(menu.getUrl())
                .icon(menu.getIcon())
                .sortOrder(menu.getSortOrder());
    }

    private SubModuleActionResponse.SubModuleActionResponseBuilder getSubModuleResponseBuilder(SubModule subModule) {
        return SubModuleActionResponse
                .builder()
                .id(subModule.getId())
                .name(String.valueOf(subModule.getName()))
                .description(subModule.getDescription())
                .sortOrder(subModule.getSortOrder());
    }

    private ModuleActionResponse.ModuleActionResponseBuilder getModuleResponseBuilder(Module module) {
        return ModuleActionResponse
                .builder()
                .id(module.getId())
                .name(String.valueOf(module.getName()))
                .description(module.getDescription())
                .sortOrder(module.getSortOrder());
    }
}

