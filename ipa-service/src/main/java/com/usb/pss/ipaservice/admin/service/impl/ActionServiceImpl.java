package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.request.ActionRequest;
import com.usb.pss.ipaservice.admin.dto.response.AdminActionResponse;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminAction;
import com.usb.pss.ipaservice.admin.repository.IpaAdminActionRepository;
import com.usb.pss.ipaservice.admin.service.iservice.ActionService;
import com.usb.pss.ipaservice.common.ExceptionConstant;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import com.usb.pss.ipaservice.utils.DaprUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActionServiceImpl implements ActionService {

    private final IpaAdminActionRepository ipaAdminActionRepository;
    @Override
    public void saveUserAction(ActionRequest actionRequest) {
        IpaAdminAction ipaAdminAction = new IpaAdminAction();
        ipaAdminAction.setName(actionRequest.name());
        ipaAdminActionRepository.save(ipaAdminAction);
    }

    @Override
    public AdminActionResponse getUserActionById(Long actionId) {
        if (DaprUtils.getUserActionFromDapr(actionId) != null){
            return DaprUtils.getUserActionFromDapr(actionId);
        }
        else {
            IpaAdminAction ipaAdminAction = ipaAdminActionRepository.findById(actionId).get();
            AdminActionResponse adminActionResponse = new AdminActionResponse();
            adminActionResponse.setId(ipaAdminAction.getId());
            adminActionResponse.setActionName(ipaAdminAction.getName());
            DaprUtils.saveUserActionInDapr(actionId,adminActionResponse);
            return adminActionResponse;
        }
    }

    @Override
    public String deleteUserActionById(Long actionId) {
        IpaAdminAction ipaAdminAction = ipaAdminActionRepository.findById(actionId)
                .orElseThrow(()-> new ResourceNotFoundException(ExceptionConstant.ACTION_NOT_FOUND));
        ipaAdminActionRepository.delete(ipaAdminAction);
        DaprUtils.deleteUserActionFromDapr(actionId);
        return "User action deleted successfully";
    }
}
