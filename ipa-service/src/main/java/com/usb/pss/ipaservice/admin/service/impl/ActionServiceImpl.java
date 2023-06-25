package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.request.ActionRequest;
import com.usb.pss.ipaservice.admin.dto.response.ActionResponse;
import com.usb.pss.ipaservice.admin.dto.response.AdminActionResponse;
import com.usb.pss.ipaservice.admin.dto.response.MenuActionResponse;
import com.usb.pss.ipaservice.admin.dto.response.ModuleActionResponse;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminAction;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminModule;
import com.usb.pss.ipaservice.admin.repository.IpaAdminActionRepository;
import com.usb.pss.ipaservice.admin.repository.IpadAdminModuleRepository;
import com.usb.pss.ipaservice.admin.service.iservice.ActionService;
import com.usb.pss.ipaservice.common.ExceptionConstant;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import com.usb.pss.ipaservice.utils.DaprUtils;
import lombok.RequiredArgsConstructor;
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
        List<IpaAdminModule> modules = ipadAdminModuleRepository.findAll();
        return modules.stream().map(
                module -> ModuleActionResponse
                        .builder()
                        .id(module.getId())
                        .name(String.valueOf(module.getName()))
                        .menuList(
                                module.getMenus().stream().map(
                                        menu -> MenuActionResponse
                                                .builder()
                                                .id(menu.getId())
                                                .name(menu.getName())
                                                .actions(
                                                        menu.getActions().stream().map(
                                                                action -> ActionResponse
                                                                        .builder()
                                                                        .id(action.getId())
                                                                        .name(action.getName())
                                                                        .build()
                                                        ).toList()
                                                )
                                                .build()
                                ).toList()
                        )
                        .build()
        ).toList();
    }
}
