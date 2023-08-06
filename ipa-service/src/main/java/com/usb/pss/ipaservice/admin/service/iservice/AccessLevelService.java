package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.request.CreateAccessLevelRequest;
import com.usb.pss.ipaservice.admin.dto.request.UpdateAccessLevelRequest;
import com.usb.pss.ipaservice.admin.dto.response.AccessLevelResponse;
import com.usb.pss.ipaservice.admin.model.entity.AccessLevel;

import java.util.Collection;
import java.util.List;

public interface AccessLevelService {
    List<AccessLevel> findAccessLevelsByIds(Collection<Long> accessLevelIds);

    List<AccessLevel> getAllAccessLevels();

    List<AccessLevelResponse> getAccessLevelResponses(Collection<AccessLevel> accessLevels);

    void createAccessLevel(CreateAccessLevelRequest createAccessLevelRequest);

    void updateAccessLevel(UpdateAccessLevelRequest updateAccessLevelRequest);
}
