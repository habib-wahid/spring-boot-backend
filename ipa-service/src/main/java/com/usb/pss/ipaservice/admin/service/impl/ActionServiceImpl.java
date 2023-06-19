package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.request.ActionRequest;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminAction;
import com.usb.pss.ipaservice.admin.repository.IpaAdminActionRepository;
import com.usb.pss.ipaservice.admin.service.iservice.ActionService;
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
        ipaAdminAction.setId(actionRequest.id());
        ipaAdminAction.setActive(actionRequest.active());
        ipaAdminActionRepository.save(ipaAdminAction);
    }

    @Override
    public IpaAdminAction getUserActionById(Long actionId) {
        IpaAdminAction ipaAdminAction = DaprUtils.getUserActionFromDapr(actionId);
        if (ipaAdminAction != null){
            return ipaAdminAction;
        }
        else {
            ipaAdminAction = ipaAdminActionRepository.findById(actionId).get();
            DaprUtils.saveUserActionInDapr(actionId,ipaAdminAction);
        }
        return ipaAdminAction;
    }

    @Override
    public String deleteUserActionById(Long actionId) {
        IpaAdminAction ipaAdminAction = ipaAdminActionRepository.findById(actionId)
                .orElseThrow(()-> new ResourceNotFoundException(String.valueOf(actionId),"Action not found"));
        ipaAdminActionRepository.delete(ipaAdminAction);
        DaprUtils.deleteUserActionFromDapr(actionId);
        return "User action deleted successfully";
    }
}
