package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.request.ActionRequest;
import com.usb.pss.ipaservice.admin.dto.response.AdminActionResponse;
import com.usb.pss.ipaservice.admin.model.entity.Action;
import com.usb.pss.ipaservice.admin.repository.ActionRepository;
import com.usb.pss.ipaservice.admin.service.iservice.ActionService;
import com.usb.pss.ipaservice.common.ExceptionConstant;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import com.usb.pss.ipaservice.utils.DaprUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class ActionServiceImpl implements ActionService {

    private final ActionRepository actionRepository;

    @Override
    public List<Action> getAllActionsByIdsWithMenu(Set<Long> actionIds) {
        return actionRepository.findActionAndFetchMenuByIdIn(actionIds);
    }


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

}
