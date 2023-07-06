package com.usb.pss.ipaservice.common.validation;

import com.usb.pss.ipaservice.admin.model.entity.PasswordPolicy;
import com.usb.pss.ipaservice.admin.service.iservice.PasswordPolicyService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private final PasswordPolicyService passwordPolicyService;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        PasswordPolicy passwordPolicy = passwordPolicyService.getPasswordPolicy();

        boolean containsDigit = false;
        boolean containsUppercase = false;
        boolean containsLowercase = false;
        boolean containsSpecialCharacter = false;

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (Character.isSpaceChar(ch)) {
                return false;
            }

            if (Character.isDigit(ch)) {
                containsDigit = true;
            } else if (Character.isUpperCase(ch)) {
                containsUppercase = true;
            } else if (Character.isLowerCase(ch)) {
                containsLowercase = true;
            } else {
                containsSpecialCharacter = true;
            }
        }

        return s.length() >= passwordPolicy.getPasswordLength() &&
            (!passwordPolicy.getContainsDigit() || containsDigit) &&
            (!passwordPolicy.getContainsUppercase() || containsUppercase) &&
            (!passwordPolicy.getContainsLowercase() || containsLowercase) &&
            (!passwordPolicy.getContainsSpecialCharacters() || containsSpecialCharacter);
    }
}
