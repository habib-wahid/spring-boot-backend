package com.usb.pss.ipaservice.admin.service.iservice;


import com.usb.pss.ipaservice.admin.dto.response.UserTypeResponse;
import com.usb.pss.ipaservice.admin.model.entity.UserType;

import java.util.List;

public interface UserTypeService {
    UserType findUserTypeById(Long userTypeId);

    List<UserTypeResponse> getAllUserTypes();
    UserTypeResponse getUserTypeResponse(UserType userType);

}
