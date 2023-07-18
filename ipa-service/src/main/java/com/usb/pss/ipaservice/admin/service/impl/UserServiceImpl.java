package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.request.ChangePassowrdRequest;
import com.usb.pss.ipaservice.admin.dto.request.RegistrationRequest;
import com.usb.pss.ipaservice.admin.dto.request.UserActionRequest;
import com.usb.pss.ipaservice.admin.dto.request.UserRoleRequest;
import com.usb.pss.ipaservice.admin.dto.request.UserStatusRequest;
import com.usb.pss.ipaservice.admin.dto.response.MenuResponse;
import com.usb.pss.ipaservice.admin.dto.response.ModuleResponse;
import com.usb.pss.ipaservice.admin.dto.response.UserResponse;
import com.usb.pss.ipaservice.admin.model.entity.Action;
import com.usb.pss.ipaservice.admin.model.entity.Role;
import com.usb.pss.ipaservice.admin.model.entity.User;
import com.usb.pss.ipaservice.admin.repository.ActionRepository;
import com.usb.pss.ipaservice.admin.repository.RoleRepository;
import com.usb.pss.ipaservice.admin.repository.UserRepository;
import com.usb.pss.ipaservice.admin.service.iservice.ModuleService;
import com.usb.pss.ipaservice.admin.service.iservice.UserService;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import com.usb.pss.ipaservice.exception.RuleViolationException;
import com.usb.pss.ipaservice.utils.LoggedUserHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static com.usb.pss.ipaservice.common.ExceptionConstant.CURRENT_PASSWORD_NOT_MATCH;
import static com.usb.pss.ipaservice.common.ExceptionConstant.DUPLICATE_USERNAME;
import static com.usb.pss.ipaservice.common.ExceptionConstant.NEW_PASSWORD_NOT_MATCH;
import static com.usb.pss.ipaservice.common.ExceptionConstant.PASSWORD_NOT_MATCH;
import static com.usb.pss.ipaservice.common.ExceptionConstant.ROLE_NOT_FOUND;
import static com.usb.pss.ipaservice.common.ExceptionConstant.USER_NOT_FOUND_BY_ID;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModuleService moduleService;
    private final RoleRepository roleRepository;
    private final ActionRepository actionRepository;

    public void createNewUser(RegistrationRequest request) {
        if (!request.password().equals(request.confirmPassword())) {
            throw new RuleViolationException(PASSWORD_NOT_MATCH);
        }

        if (userRepository.existsByUsername(request.username())) {
            throw new RuleViolationException(DUPLICATE_USERNAME);
        }

        var user = User.builder().firstName(request.firstName()).lastName(request.lastName()).email(request.email())
            .username(request.username()).password(passwordEncoder.encode(request.password())).active(true).build();

        userRepository.save(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().filter(Objects::nonNull).map(user -> {
            UserResponse userResponse = new UserResponse();
            prepareResponse(user, userResponse);
            return userResponse;
        }).toList();
    }

    private void prepareResponse(User user, UserResponse userResponse) {
        userResponse.setId(user.getId());
        userResponse.setName(user.getUsername());

    }

    @Override
    public void addAdditionalAction(UserActionRequest userActionRequest) {
        User user = userRepository.findUserFetchAdditionalActionsById(userActionRequest.userId())
            .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_BY_ID));

        List<Action> actions = actionRepository.findByIdIn(userActionRequest.actionIds());

        user.getAdditionalActions().addAll(actions);
        userRepository.save(user);
    }

//    @Override
//    public Set<MenuResponse> getAllPermittedMenuByUser(User user) {
//        return user.getPermittedMenus().stream().map(menu -> {
//            MenuResponse menuResponse = new MenuResponse();
//            menuService.prepareResponse(menu, menuResponse);
//            return menuResponse;
//        }).collect(Collectors.toSet());
//    }

    @Override
    public List<ModuleResponse> getModuleWiseUserActions(Long userId) {
        return moduleService.getModuleWiseUserActions(userId);
    }

    @Override
    public void updateUserRole(UserRoleRequest userRoleRequest) {
        User user = userRepository.findById(userRoleRequest.userId())
            .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_BY_ID));

        Role role = roleRepository.findById(userRoleRequest.roleId())
            .orElseThrow(() -> new ResourceNotFoundException(ROLE_NOT_FOUND));

        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public void updateUserStatusInfo(UserStatusRequest request) {
        User user = userRepository.findById(request.userId())
            .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_BY_ID));

        user.setActive(request.userStatus());
        userRepository.save(user);
    }

    @Override
    public Set<MenuResponse> getUserAllPermittedMenu() {
//        Optional<Long> optionalUserId = LoggedUserHelper.getCurrentUserId();
//        optionalUserId.ifPresent(userId ->
//            getUserById(userId).getPermittedMenus().stream()
//                .map(menu -> {
//                    MenuResponse menuResponse = new MenuResponse();
//                    menuService.prepareResponse(menu, menuResponse);
//                    return menuResponse;
//                }).collect(Collectors.toSet())
//        );
        return new HashSet<>();
    }


    @Override
    public void changeUserPassword(ChangePassowrdRequest request) {
        Optional<User> optionalUser = LoggedUserHelper.getCurrentUser();
        optionalUser.ifPresent(user -> {
            if ((passwordEncoder.matches(request.currentPassword(), user.getPassword()))) {
                if (request.newPassword().equals(request.confirmPassword())) {
                    user.setPassword(passwordEncoder.encode(request.newPassword()));
                    userRepository.save(user);
                } else {
                    throw new RuleViolationException(NEW_PASSWORD_NOT_MATCH);
                }

            } else {
                throw new RuleViolationException(CURRENT_PASSWORD_NOT_MATCH);
            }
        });

    }

}
