package com.usb.pss.ipaservice.admin.service.impl;

import static com.usb.pss.ipaservice.common.ExceptionConstant.PASSWORD_POLICY_NOT_FOUND;

import com.usb.pss.ipaservice.admin.dto.request.PasswordPolicyRequest;
import com.usb.pss.ipaservice.admin.model.entity.PasswordPolicy;
import com.usb.pss.ipaservice.admin.repository.PasswordPolicyRepository;
import com.usb.pss.ipaservice.admin.service.iservice.PasswordPolicyService;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordPolicyServiceImpl implements PasswordPolicyService {

    private final PasswordPolicyRepository passwordPolicyRepository;

    @Override
    public void updatePassPolicy(PasswordPolicyRequest request) {
        PasswordPolicy passwordPolicy = getPasswordPolicy();

        passwordPolicy.setPasswordLength(request.passwordLength());
        passwordPolicy.setContainsUppercase(request.containsUppercase());
        passwordPolicy.setContainsLowercase(request.containsLowercase());
        passwordPolicy.setContainsDigit(request.containsDigit());
        passwordPolicy.setContainsSpecialCharacters(request.containsSpecialCharacters());

        passwordPolicyRepository.save(passwordPolicy);
    }

    @Override
    public PasswordPolicy getPasswordPolicy() {
        return passwordPolicyRepository.findById(1L)
            .orElseThrow(() -> new ResourceNotFoundException(PASSWORD_POLICY_NOT_FOUND));
    }
}
