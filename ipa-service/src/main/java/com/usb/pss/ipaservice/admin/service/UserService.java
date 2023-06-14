package com.usb.pss.ipaservice.admin.service;

import com.usb.pss.ipaservice.admin.dto.request.RegistrationRequest;
import com.usb.pss.ipaservice.utils.GenericResponse;

public interface UserService {

    GenericResponse registerUser(RegistrationRequest request);

}
