package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.request.ActionRequest;
import com.usb.pss.ipaservice.admin.dto.request.RoleActionRequest;
import com.usb.pss.ipaservice.admin.dto.response.ActionResponse;
import com.usb.pss.ipaservice.admin.dto.response.AdminActionResponse;
import com.usb.pss.ipaservice.admin.dto.response.MenuResponse;
import com.usb.pss.ipaservice.admin.dto.response.ModuleResponse;
import com.usb.pss.ipaservice.admin.dto.response.SubModuleResponse;
import com.usb.pss.ipaservice.admin.model.entity.Action;
import com.usb.pss.ipaservice.admin.model.entity.Module;
import com.usb.pss.ipaservice.admin.model.entity.Role;
import com.usb.pss.ipaservice.admin.repository.ActionRepository;
import com.usb.pss.ipaservice.admin.repository.ModuleRepository;
import com.usb.pss.ipaservice.admin.repository.RoleRepository;
import com.usb.pss.ipaservice.admin.service.iservice.ActionService;
import com.usb.pss.ipaservice.admin.service.iservice.RoleService;
import com.usb.pss.ipaservice.common.ExceptionConstant;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import com.usb.pss.ipaservice.utils.DaprUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActionServiceImpl implements ActionService {

    private final ActionRepository actionRepository;
    private final ModuleRepository moduleRepository;
    private final RoleService roleService;
    private final RoleRepository roleRepository;

    @Override
    public void saveUserAction(ActionRequest actionRequest) {
        Action action = new Action();
        action.setName(actionRequest.name());
        actionRepository.save(action);
    }

    @Override
    public AdminActionResponse getUserActionById(Long actionId) {
        if (DaprUtils.getUserActionFromDapr(actionId) != null) {
            return DaprUtils.getUserActionFromDapr(actionId);
        } else {
            Action action = actionRepository.findById(actionId).get();
            AdminActionResponse adminActionResponse = new AdminActionResponse();
            adminActionResponse.setId(action.getId());
            adminActionResponse.setActionName(action.getName());
            DaprUtils.saveUserActionInDapr(actionId, adminActionResponse);
            return adminActionResponse;
        }
    }

    @Override
    public String deleteUserActionById(Long actionId) {
        Action action = actionRepository.findById(actionId)
            .orElseThrow(() -> new ResourceNotFoundException(ExceptionConstant.ACTION_NOT_FOUND));
        actionRepository.delete(action);
        DaprUtils.deleteUserActionFromDapr(actionId);
        return "User action deleted successfully";
    }

    @Override
    public List<ModuleResponse> getModuleActions() {
        List<Module> modules = moduleRepository.findAllByParentModuleIsNull();
        return getModuleActionsByModules(modules);
    }

    @Override
    public void updateRoleAction(RoleActionRequest request) {
        Role role = roleService.getRoleById(request.roleId());
        List<Action> updatedActions = actionRepository.findAllById(request.actionIds());
        role.getPermittedActions().addAll(updatedActions);
        role.getPermittedActions().retainAll(updatedActions);
        roleRepository.save(role);
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
    public List<ModuleResponse> getRoleWisePermittedActions(Long roleId) {
        Role role = roleService.getRoleById(roleId);
        List<Module> roleWiseModules = moduleRepository.getAllModulesForRole(role.getId());
        return getModuleActionsByModules(roleWiseModules);
    }
}
