package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.request.ActionRequest;
import com.usb.pss.ipaservice.admin.dto.response.ActionResponse;
import com.usb.pss.ipaservice.admin.dto.response.AdminActionResponse;
import com.usb.pss.ipaservice.admin.dto.response.MenuActionResponse;
import com.usb.pss.ipaservice.admin.dto.response.ModuleActionResponse;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminAction;
import com.usb.pss.ipaservice.admin.repository.IpaAdminActionRepository;
import com.usb.pss.ipaservice.admin.repository.IpadAdminModuleRepository;
import com.usb.pss.ipaservice.admin.service.iservice.ActionService;
import com.usb.pss.ipaservice.common.ExceptionConstant;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import com.usb.pss.ipaservice.utils.DaprUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ActionServiceImpl implements ActionService {

    private final IpaAdminActionRepository ipaAdminActionRepository;
    private final IpadAdminModuleRepository ipadAdminModuleRepository;

    @Override
    public void saveUserAction(ActionRequest actionRequest) {
        IpaAdminAction ipaAdminAction = new IpaAdminAction();
        ipaAdminAction.setName(actionRequest.name());
        ipaAdminActionRepository.save(ipaAdminAction);
    }

    @Override
    public AdminActionResponse getUserActionById(Long actionId) {
        if (DaprUtils.getUserActionFromDapr(actionId) != null) {
            return DaprUtils.getUserActionFromDapr(actionId);
        } else {
            IpaAdminAction ipaAdminAction = ipaAdminActionRepository.findById(actionId).get();
            AdminActionResponse adminActionResponse = new AdminActionResponse();
            adminActionResponse.setId(ipaAdminAction.getId());
            adminActionResponse.setActionName(ipaAdminAction.getName());
            DaprUtils.saveUserActionInDapr(actionId, adminActionResponse);
            return adminActionResponse;
        }
    }

    @Override
    public String deleteUserActionById(Long actionId) {
        IpaAdminAction ipaAdminAction = ipaAdminActionRepository.findById(actionId)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionConstant.ACTION_NOT_FOUND));
        ipaAdminActionRepository.delete(ipaAdminAction);
        DaprUtils.deleteUserActionFromDapr(actionId);
        return "User action deleted successfully";
    }

    @Override
    public List<ModuleActionResponse> getModuleActions() {
        List<Map<String, Object>> actionsByMenuAndModule = ipadAdminModuleRepository.getActionsByMenuAndModule();
        List<ModuleActionResponse> allModuleActionResponses = new ArrayList<>();
        for (Map actionByMenuAndModule : actionsByMenuAndModule) {
            ModuleActionResponse moduleActionResponse = checkModuleExist(
                    allModuleActionResponses,
                    Long.valueOf(actionByMenuAndModule.get("module_id").toString()));
            if (moduleActionResponse != null) {
                List<MenuActionResponse> menuActionResponses = moduleActionResponse.getModuleMenuList();
                MenuActionResponse menuActions = checkMenuExist(
                        menuActionResponses,
                        Long.valueOf(actionByMenuAndModule.get("menu_id").toString()));
                if (menuActions == null) {
                    MenuActionResponse menuActionResponse = new MenuActionResponse();
                    menuActionResponse.setMenuId(Long.valueOf(actionByMenuAndModule.get("menu_id").toString()));
                    menuActionResponse.setMenuName(actionByMenuAndModule.get("menu_name").toString());
                    menuActionResponse.setActions(Arrays.asList(
                            new ActionResponse(
                                    Long.valueOf(actionByMenuAndModule.get("action_id").toString()),
                                    actionByMenuAndModule.get("action_name").toString())
                    ));
                    if (moduleActionResponse.getModuleMenuList() != null) {
                        ArrayList<MenuActionResponse> mod = new ArrayList<>();
                        mod.addAll(menuActionResponses);
                        mod.add(menuActionResponse);
                        moduleActionResponse.setModuleMenuList(mod);
                    } else {
                        moduleActionResponse.setModuleMenuList(Arrays.asList(menuActionResponse));
                    }
                } else {
                    List<ActionResponse> actions = new ArrayList<>();
                    actions.addAll(menuActions.getActions());
                    actions.add(new ActionResponse(
                            Long.valueOf(actionByMenuAndModule.get("action_id").toString()),
                            actionByMenuAndModule.get("action_name").toString()));
                    menuActions.setActions(actions);
                }
            } else {
                ModuleActionResponse adminModules = new ModuleActionResponse();
                adminModules.setModuleId(Long.valueOf(actionByMenuAndModule.get("module_id").toString()));
                adminModules.setModuleName(actionByMenuAndModule.get("module_name").toString());
                allModuleActionResponses.add(adminModules);
            }
        }
        return allModuleActionResponses;
    }

    public ModuleActionResponse checkModuleExist(List<ModuleActionResponse> moduleActionResponses, Long moduleId) {
        for (ModuleActionResponse moduleActionResponse : moduleActionResponses) {
            if (moduleActionResponse.getModuleId() == moduleId) {
                return moduleActionResponse;
            }
        }
        return null;
    }

    public MenuActionResponse checkMenuExist(List<MenuActionResponse> menuActionResponses, Long menuId) {
        if (menuActionResponses == null)
            return null;
        for (MenuActionResponse menuActionResponse : menuActionResponses) {
            if (menuActionResponse.getMenuId() == menuId)
                return menuActionResponse;
        }
        return null;
    }
}
