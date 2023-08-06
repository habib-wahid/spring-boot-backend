package com.usb.pss.ipaservice.admin.service.impl;


import com.usb.pss.ipaservice.admin.dto.response.UserTypeResponse;
import com.usb.pss.ipaservice.admin.model.entity.UserType;
import com.usb.pss.ipaservice.admin.repository.UserTypeRepository;
import com.usb.pss.ipaservice.admin.service.iservice.UserTypeService;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.usb.pss.ipaservice.common.constants.ExceptionConstant.USER_TYPE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserTypeServiceImpl implements UserTypeService {
    private final UserTypeRepository userTypeRepository;

    @Override
    public UserType findUserTypeById(Long userTypeId) {
        return userTypeRepository.findById(userTypeId)
            .orElseThrow(() -> new ResourceNotFoundException(USER_TYPE_NOT_FOUND));
    }

    @Override
    public UserTypeResponse getUserTypeResponse(UserType userType) {
        return new UserTypeResponse(userType.getId(), userType.getName());
    }


}
