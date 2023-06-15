package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.request.RegistrationRequest;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminUser;
import com.usb.pss.ipaservice.admin.repository.IpaAdminUserRepository;
import com.usb.pss.ipaservice.admin.service.UserService;
import com.usb.pss.ipaservice.exception.RuleViolationException;
import com.usb.pss.ipaservice.utils.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.usb.pss.ipaservice.common.ExceptionConstants.ALREADY_EXISTS;
import static com.usb.pss.ipaservice.common.ExceptionConstants.PASSWORD_NOT_MATCH;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final IpaAdminUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public GenericResponse registerUser(RegistrationRequest request) {
        if (!request.password().equals(request.confirmPassword())) {
            throw new RuleViolationException(PASSWORD_NOT_MATCH, "Password does not match...");
        }

        if (userRepository.existsByUsername(request.username())) {
            throw new RuleViolationException(ALREADY_EXISTS, "User already exists with this username...");
        }

        var user = IpaAdminUser.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .build();
        userRepository.save(user);

        return new GenericResponse(HttpStatus.OK, "Successfully Registered");
    }


}
