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
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        PasswordPolicy passwordPolicy = passwordPolicyService.getPasswordPolicy();

        boolean containsDigit = containsDigitCharacter(password);
        boolean containsUppercase = containsUppercaseCharacter(password);
        boolean containsLowercase = containsLowercaseCharacter(password);
        boolean containsSpecialCharacter = containsSpecialCharacter(password);

        return password.length() >= passwordPolicy.getPasswordLength() &&
            (!passwordPolicy.getContainsDigit() || containsDigit) &&
            (!passwordPolicy.getContainsUppercase() || containsUppercase) &&
            (!passwordPolicy.getContainsLowercase() || containsLowercase) &&
            (!passwordPolicy.getContainsSpecialCharacters() || containsSpecialCharacter);
    }

    private boolean containsDigitCharacter(String password) {
        return password.chars().anyMatch(Character::isDigit);
    }

    private boolean containsUppercaseCharacter(String password) {
        return password.chars().anyMatch(Character::isUpperCase);
    }

    private boolean containsLowercaseCharacter(String password) {
        return password.chars().anyMatch(Character::isLowerCase);
    }

    private boolean containsSpecialCharacter(String password) {
        return !password.matches("[A-Za-z0-9]*");
    }
}
