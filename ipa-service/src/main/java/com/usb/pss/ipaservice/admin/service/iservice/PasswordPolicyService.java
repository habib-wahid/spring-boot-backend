package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.request.PasswordPolicyRequest;
import com.usb.pss.ipaservice.admin.model.entity.PasswordPolicy;

public interface PasswordPolicyService {

    void updatePassPolicy(PasswordPolicyRequest request);
    PasswordPolicy getPasswordPolicyById(Long id);
}
