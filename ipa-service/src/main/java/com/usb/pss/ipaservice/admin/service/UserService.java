package com.usb.pss.ipaservice.admin.service;

import com.usb.pss.ipaservice.admin.dto.request.RegistrationRequest;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminUser;
import com.usb.pss.ipaservice.admin.repository.IpaAdminUserRepository;
import com.usb.pss.ipaservice.exception.RuleViolationException;
import com.usb.pss.ipaservice.utils.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.usb.pss.ipaservice.common.ExceptionConstants.PASSWORD_NOT_MATCH;

@Service
@RequiredArgsConstructor
public class UserService {
    private final IpaAdminUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public GenericResponse registerUser(RegistrationRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuleViolationException(PASSWORD_NOT_MATCH, "Password does not match...");
        }

        var user = IpaAdminUser.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .active(true)
                .build();
        repository.save(user);

        return new GenericResponse(HttpStatus.OK, "Successfully Registered");
    }


}
