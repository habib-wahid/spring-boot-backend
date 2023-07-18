package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.request.PasswordPolicyRequest;
import com.usb.pss.ipaservice.admin.model.entity.PasswordPolicy;

import java.util.List;
import java.util.Map;

public interface PasswordPolicyService {

    void updatePassPolicy(PasswordPolicyRequest request);
    PasswordPolicy getPasswordPolicy();
    List<Map<String, Object>> getPasswordPolicies();
}
