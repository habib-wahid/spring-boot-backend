package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.request.CreateAccessLevelRequest;
import com.usb.pss.ipaservice.admin.dto.request.UpdateAccessLevelRequest;
import com.usb.pss.ipaservice.admin.dto.response.AccessLevelResponse;
import com.usb.pss.ipaservice.admin.model.entity.AccessLevel;
import com.usb.pss.ipaservice.admin.repository.AccessLevelRepository;
import com.usb.pss.ipaservice.admin.service.iservice.AccessLevelService;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

import static com.usb.pss.ipaservice.common.constants.ExceptionConstant.ACCESS_LEVEL_NOT_FOUND;

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

    @Override
    public void createAccessLevel(CreateAccessLevelRequest createAccessLevelRequest) {
        if (!checkAccessLevelExistsByName(createAccessLevelRequest.name())) {
            accessLevelRepository.save(new AccessLevel(createAccessLevelRequest.name()));
        }
    }

    @Override
    public void updateAccessLevel(UpdateAccessLevelRequest updateAccessLevelRequest) {
        AccessLevel accessLevel = getAccessLevelById(updateAccessLevelRequest.id());
        if (!checkAccessLevelExistsByName(updateAccessLevelRequest.name())) {
            accessLevel.setName(updateAccessLevelRequest.name());
            accessLevelRepository.save(accessLevel);
        }
    }

    private AccessLevelResponse getAccessLevelResponse(AccessLevel accessLevel) {
        return new AccessLevelResponse(accessLevel.getId(), accessLevel.getName());
    }

    private AccessLevel getAccessLevelById(Long id) {
        return accessLevelRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(ACCESS_LEVEL_NOT_FOUND));
    }

    private Boolean checkAccessLevelExistsByName(String name) {
        return accessLevelRepository.existsByName(name);
    }
}
