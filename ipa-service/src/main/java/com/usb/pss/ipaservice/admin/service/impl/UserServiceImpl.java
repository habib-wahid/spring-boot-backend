package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.request.UserGroupRequest;
import com.usb.pss.ipaservice.admin.dto.request.RegistrationRequest;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminGroup;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminUser;
import com.usb.pss.ipaservice.admin.repository.IpaAdminUserRepository;
import com.usb.pss.ipaservice.admin.service.iservice.GroupService;
import com.usb.pss.ipaservice.admin.service.iservice.UserService;
import com.usb.pss.ipaservice.common.ExceptionConstant;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import com.usb.pss.ipaservice.exception.RuleViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.usb.pss.ipaservice.common.ExceptionConstant.DUPLICATE_USERNAME;
import static com.usb.pss.ipaservice.common.ExceptionConstant.PASSWORD_NOT_MATCH;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final IpaAdminUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final GroupService groupService;

    public void registerUser(RegistrationRequest request) {
        if (!request.password().equals(request.confirmPassword())) {
            throw new RuleViolationException(PASSWORD_NOT_MATCH);
        }

        if (userRepository.existsByUsername(request.username())) {
            throw new RuleViolationException(DUPLICATE_USERNAME);
        }

        var user = IpaAdminUser.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .active(true)
                .build();

        userRepository.save(user);
    }

    private IpaAdminUser getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionConstant.USER_NOT_FOUND_BY_ID));
    }

    @Override
    public void updateGroup(UserGroupRequest userGroupRequest) {
        IpaAdminGroup group = groupService.getGroupById(userGroupRequest.groupId());
        IpaAdminUser user = getUserById(userGroupRequest.userId());
        user.setGroup(group);
        userRepository.save(user);

    }


    @Override
    public void removeGroup(UserGroupRequest userGroupRequest) {
        IpaAdminGroup group = groupService.getGroupById(userGroupRequest.groupId());
        IpaAdminUser user = getUserById(userGroupRequest.userId());
        if (user.getGroup().equals(group)) {
            user.setGroup(null);
        }
        userRepository.save(user);
    }


}
