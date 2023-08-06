package com.usb.pss.ipaservice.admin.service.iservice;


import com.usb.pss.ipaservice.admin.dto.response.UserTypeResponse;
import com.usb.pss.ipaservice.admin.model.entity.UserType;

public interface UserTypeService {
    UserType findUserTypeById(Long userTypeId);


    UserTypeResponse getUserTypeResponse(UserType userType);

}
