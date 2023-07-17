package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.response.AdminActionResponse;
import com.usb.pss.ipaservice.admin.model.entity.Action;
import com.usb.pss.ipaservice.admin.repository.ActionRepository;
import com.usb.pss.ipaservice.admin.service.iservice.ActionService;
import com.usb.pss.ipaservice.utils.DaprUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ActionServiceImpl implements ActionService {

    private final ActionRepository actionRepository;


    @Override
    public AdminActionResponse getUserActionById(Long actionId) {
        if (DaprUtils.getUserActionFromDapr(actionId) != null) {
            return DaprUtils.getUserActionFromDapr(actionId);
        } else {
            AdminActionResponse adminActionResponse = new AdminActionResponse();
            Optional<Action> actionOptional = actionRepository.findById(actionId);
            actionOptional.ifPresent(action -> {
                adminActionResponse.setId(action.getId());
                adminActionResponse.setActionName(action.getName());
                DaprUtils.saveUserActionInDapr(actionId, adminActionResponse);
            });
            return adminActionResponse;
        }
    }

}
