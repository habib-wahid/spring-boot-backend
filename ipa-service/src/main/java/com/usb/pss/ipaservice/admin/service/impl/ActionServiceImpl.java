package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.request.ActionRequest;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminAction;
import com.usb.pss.ipaservice.admin.repository.IpaAdminActionRepository;
import com.usb.pss.ipaservice.admin.service.iservice.ActionService;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActionServiceImpl implements ActionService {

    private final IpaAdminActionRepository ipaAdminActionRepository;
    private final String DAPR_SATE_STORE = "ipa-state-store";

    @Override
    public IpaAdminAction saveUserAction(ActionRequest actionRequest) {
        IpaAdminAction ipaAdminAction = new IpaAdminAction();
        ipaAdminAction.setId(actionRequest.id());
        ipaAdminAction.setActive(actionRequest.active());
        return ipaAdminActionRepository.save(ipaAdminAction);
    }

    @Override
    public IpaAdminAction getUserAction(Long actionId) {
        DaprClient daprClient = new DaprClientBuilder().build();
        IpaAdminAction ipaAdminAction = daprClient.getState(DAPR_SATE_STORE,String.valueOf(actionId),IpaAdminAction.class).block().getValue();
        if (ipaAdminAction != null){
            return ipaAdminAction;
        }
        else {
            ipaAdminAction = ipaAdminActionRepository.findById(actionId).get();
            daprClient.saveState(DAPR_SATE_STORE,String.valueOf(actionId),ipaAdminAction).block();
        }
        return ipaAdminAction;
    }

    @Override
    public String deleteUserAction(Long actionId) {
        IpaAdminAction ipaAdminAction = ipaAdminActionRepository.findById(actionId)
                .orElseThrow(()-> new ResourceNotFoundException(String.valueOf(actionId),"Action not found"));
        ipaAdminActionRepository.delete(ipaAdminAction);
        DaprClient daprClient = new DaprClientBuilder().build();
        daprClient.deleteState(DAPR_SATE_STORE,String.valueOf(actionId)).block();
        return "User action deleted successfully";
    }
}
