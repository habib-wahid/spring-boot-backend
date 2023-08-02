package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.response.AdminActionResponse;
import com.usb.pss.ipaservice.admin.model.entity.Action;

import java.util.Collection;
import java.util.List;

public interface ActionService {

    AdminActionResponse getUserActionById(Long actionId);

    List<Action> getActionsByIds(Collection<Long> actionIds);

}
