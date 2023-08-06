package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.response.AccessLevelResponse;
import com.usb.pss.ipaservice.admin.model.entity.AccessLevel;
import com.usb.pss.ipaservice.admin.repository.AccessLevelRepository;
import com.usb.pss.ipaservice.admin.service.iservice.AccessLevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccessLevelServiceImpl implements AccessLevelService {
    private final AccessLevelRepository accessLevelRepository;

    @Override
    public List<AccessLevel> findAccessLevelsByIds(Collection<Long> accessLevelIds) {
        return accessLevelRepository.findByIdIn(accessLevelIds);
    }

    @Override
    public List<AccessLevel> getAllAccessLevels() {
        return accessLevelRepository.findAll();
    }

    @Override
    public List<AccessLevelResponse> getAccessLevelResponses(Collection<AccessLevel> accessLevels) {
        return accessLevels
            .stream()
            .map(this::getAccessLevelResponse)
            .toList();
    }

    private AccessLevelResponse getAccessLevelResponse(AccessLevel accessLevel) {
        return new AccessLevelResponse(accessLevel.getId(), accessLevel.getName());
    }
}
