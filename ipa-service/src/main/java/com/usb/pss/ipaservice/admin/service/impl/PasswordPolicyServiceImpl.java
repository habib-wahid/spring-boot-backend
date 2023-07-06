package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.request.PasswordPolicyRequest;
import com.usb.pss.ipaservice.admin.model.entity.PasswordPolicy;
import com.usb.pss.ipaservice.admin.repository.PasswordPolicyRepository;
import com.usb.pss.ipaservice.admin.service.iservice.PasswordPolicyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordPolicyServiceImpl implements PasswordPolicyService {

    private final PasswordPolicyRepository passwordPolicyRepository;

    @Override
    public void updatePassPolicy(PasswordPolicyRequest request) {
        PasswordPolicy passwordPolicy;
        List<PasswordPolicy> passwordPolicies = passwordPolicyRepository.findAll();

        if (!passwordPolicies.isEmpty()) {
            passwordPolicy = passwordPolicies.get(0);
            prepareEntity(passwordPolicy, request);
        } else {
            passwordPolicy = PasswordPolicy.builder()
                .passwordLength(request.passwordLength())
                .containsUppercase(request.containsUppercase())
                .containsLowercase(request.containsLowercase())
                .containsDigit(request.containsDigit())
                .containsSpecialCharacters(request.containsSpecialCharacters())
                .build();
        }

        passwordPolicyRepository.save(passwordPolicy);
    }

    private void prepareEntity(PasswordPolicy passwordPolicy, PasswordPolicyRequest request) {
        passwordPolicy.setPasswordLength(request.passwordLength());
        passwordPolicy.setContainsUppercase(request.containsUppercase());
        passwordPolicy.setContainsLowercase(request.containsLowercase());
        passwordPolicy.setContainsDigit(request.containsDigit());
        passwordPolicy.setContainsSpecialCharacters(request.containsSpecialCharacters());
    }
}
