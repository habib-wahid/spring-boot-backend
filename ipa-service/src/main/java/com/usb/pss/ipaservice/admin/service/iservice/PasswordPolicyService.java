package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.request.PasswordPolicyRequest;

public interface PasswordPolicyService {

    void updatePassPolicy(PasswordPolicyRequest request);
}
