package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.request.RegistrationRequest;
import com.usb.pss.ipaservice.admin.dto.request.UserActionRequest;
import com.usb.pss.ipaservice.admin.dto.response.MenuResponse;
import com.usb.pss.ipaservice.admin.dto.response.ModuleResponse;
import com.usb.pss.ipaservice.admin.dto.response.UserResponse;
import com.usb.pss.ipaservice.admin.model.entity.Action;
import com.usb.pss.ipaservice.admin.model.entity.Menu;
import com.usb.pss.ipaservice.admin.model.entity.User;
import com.usb.pss.ipaservice.admin.repository.UserRepository;
import com.usb.pss.ipaservice.admin.service.iservice.ActionService;
import com.usb.pss.ipaservice.admin.service.iservice.MenuService;
import com.usb.pss.ipaservice.admin.service.iservice.ModuleService;
import com.usb.pss.ipaservice.admin.service.iservice.UserService;
import com.usb.pss.ipaservice.common.ExceptionConstant;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import com.usb.pss.ipaservice.exception.RuleViolationException;
import com.usb.pss.ipaservice.utils.LoggedUserHelper;
import java.util.Optional;
import java.util.List;
import java.util.Set;
import java.util.Objects;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static com.usb.pss.ipaservice.common.ExceptionConstant.DUPLICATE_USERNAME;
import static com.usb.pss.ipaservice.common.ExceptionConstant.PASSWORD_NOT_MATCH;



@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MenuService menuService;
    private final ActionService actionService;
    private final ModuleService moduleService;

    public void registerUser(RegistrationRequest request) {
        if (!request.password().equals(request.confirmPassword())) {
            throw new RuleViolationException(PASSWORD_NOT_MATCH);
        }

        if (userRepository.existsByUsername(request.username())) {
            throw new RuleViolationException(DUPLICATE_USERNAME);
        }

        var user = User.builder()
            .firstName(request.firstName())
            .lastName(request.lastName())
            .email(request.email())
            .username(request.username())
            .password(passwordEncoder.encode(request.password()))
            .active(true)
            .build();

        userRepository.save(user);
    }

    private User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(ExceptionConstant.USER_NOT_FOUND_BY_ID));
    }

    private User getUserWithMenuAndActionById(Long userId) {
        return userRepository.findUserWithMenusAndActionsById(userId)
            .orElseThrow(() -> new ResourceNotFoundException(ExceptionConstant.USER_NOT_FOUND_BY_ID));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
            .stream()
            .filter(Objects::nonNull)
            .map(user -> {
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
    public void updateUserActions(UserActionRequest userActionRequest) {
        User user = getUserWithMenuAndActionById(userActionRequest.userId());
        List<Action> actions = actionService.getAllActionsByIdsWithMenu(userActionRequest.actionIds());
        List<Menu> menus = actions.stream()
            .map(Action::getMenu)
            .toList();
        user.getPermittedMenus().addAll(menus);
        user.getPermittedMenus().retainAll(menus);
        user.getPermittedActions().addAll(actions);
        user.getPermittedActions().retainAll(actions);
        userRepository.save(user);
    }

    @Override
    public Set<MenuResponse> getAllPermittedMenuByUser(User user) {
        return user.getPermittedMenus().stream()
            .map(menu -> {
                MenuResponse menuResponse = new MenuResponse();
                menuService.prepareResponse(menu, menuResponse);
                return menuResponse;
            }).collect(Collectors.toSet());
    }

    @Override
    public List<ModuleResponse> getModuleWiseUserActions(Long userId) {
        return moduleService.getModuleWiseUserActions(userId);
    }

    @Override
    public Set<MenuResponse> getUserAllPermittedMenu() {
        Optional<Long> optionalUserId = LoggedUserHelper.getCurrentUserId();
        optionalUserId.ifPresent(userId ->
            getUserById(userId).getPermittedMenus().stream()
                    .map(menu -> {
                        MenuResponse menuResponse = new MenuResponse();
                        menuService.prepareResponse(menu, menuResponse);
                        return menuResponse;
                    }).collect(Collectors.toSet())
        );
        return new HashSet<>();
    }

}
