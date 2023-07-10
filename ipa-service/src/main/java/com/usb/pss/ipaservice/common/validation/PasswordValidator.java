package com.usb.pss.ipaservice.common.validation;

import com.usb.pss.ipaservice.admin.model.entity.PasswordPolicy;
import com.usb.pss.ipaservice.admin.service.iservice.PasswordPolicyService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private final PasswordPolicyService passwordPolicyService;

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        PasswordPolicy passwordPolicy = passwordPolicyService.getPasswordPolicy();

        Predicate<String> containsSpaceCharacter = p -> p.contains(" ");
        Predicate<String> containsDigitCharacter = p -> p.chars().anyMatch(Character::isDigit);
        Predicate<String> containsUppercaseCharacter = p -> p.chars().anyMatch(Character::isUpperCase);
        Predicate<String> containsLowercaseCharacter = p -> p.chars().anyMatch(Character::isLowerCase);
        Predicate<String> containsSpecialCharacter = p -> !p.matches("[A-Za-z0-9]*");

        return Stream.of(
                containsSpaceCharacter.negate(),
                pass -> password.length() >= passwordPolicy.getPasswordLength(),
                pass -> !passwordPolicy.getContainsDigit() || containsDigitCharacter.test(password),
                pass -> !passwordPolicy.getContainsUppercase() || containsUppercaseCharacter.test(password),
                pass -> !passwordPolicy.getContainsLowercase() || containsLowercaseCharacter.test(password),
                pass->  !passwordPolicy.getContainsSpecialCharacters() || containsSpecialCharacter.test(password)
        ).allMatch(validation -> validation.test(password));

    }

}
