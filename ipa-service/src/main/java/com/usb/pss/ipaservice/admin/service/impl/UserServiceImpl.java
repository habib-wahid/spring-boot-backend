package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.request.ChangePasswordRequest;
import com.usb.pss.ipaservice.admin.dto.request.RegistrationRequest;
import com.usb.pss.ipaservice.admin.dto.request.UpdateUserInfoRequest;
import com.usb.pss.ipaservice.admin.dto.request.UserActionRequest;
import com.usb.pss.ipaservice.admin.dto.request.UserGroupRequest;
import com.usb.pss.ipaservice.admin.dto.request.UserStatusRequest;
import com.usb.pss.ipaservice.admin.dto.response.PersonalInfoResponse;
import com.usb.pss.ipaservice.admin.service.iservice.ActionService;
import com.usb.pss.ipaservice.admin.service.iservice.AirportService;
import com.usb.pss.ipaservice.admin.service.iservice.CurrencyService;
import com.usb.pss.ipaservice.admin.service.iservice.DepartmentService;
import com.usb.pss.ipaservice.admin.service.iservice.DesignationService;
import com.usb.pss.ipaservice.admin.service.iservice.PointOfSalesService;
import com.usb.pss.ipaservice.admin.service.iservice.UserTypeService;
import com.usb.pss.ipaservice.admin.dto.response.MenuActionResponse;
import com.usb.pss.ipaservice.admin.dto.response.ModuleActionResponse;
import com.usb.pss.ipaservice.admin.dto.response.UserGroupResponse;
import com.usb.pss.ipaservice.admin.dto.response.UserPersonalInfoResponse;
import com.usb.pss.ipaservice.admin.dto.response.UserResponse;
import com.usb.pss.ipaservice.admin.model.entity.Action;
import com.usb.pss.ipaservice.inventory.model.entity.Airport;
import com.usb.pss.ipaservice.admin.model.entity.Currency;
import com.usb.pss.ipaservice.admin.model.entity.Department;
import com.usb.pss.ipaservice.admin.model.entity.Designation;
import com.usb.pss.ipaservice.admin.model.entity.Group;
import com.usb.pss.ipaservice.admin.model.entity.PersonalInfo;
import com.usb.pss.ipaservice.admin.model.entity.PointOfSale;
import com.usb.pss.ipaservice.admin.model.entity.User;
import com.usb.pss.ipaservice.admin.repository.GroupRepository;
import com.usb.pss.ipaservice.admin.repository.UserRepository;
import com.usb.pss.ipaservice.admin.service.iservice.ModuleService;
import com.usb.pss.ipaservice.admin.service.iservice.UserService;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import com.usb.pss.ipaservice.exception.RuleViolationException;
import com.usb.pss.ipaservice.utils.LoggedUserHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static com.usb.pss.ipaservice.common.constants.ExceptionConstant.CURRENT_PASSWORD_NOT_MATCH;
import static com.usb.pss.ipaservice.common.constants.ExceptionConstant.DUPLICATE_USERNAME;
import static com.usb.pss.ipaservice.common.constants.ExceptionConstant.GROUP_NOT_FOUND;
import static com.usb.pss.ipaservice.common.constants.ExceptionConstant.PASSWORD_CONFIRM_PASSWORD_NOT_MATCH;
import static com.usb.pss.ipaservice.common.constants.ExceptionConstant.USER_NOT_FOUND_BY_ID;
import static com.usb.pss.ipaservice.common.constants.ExceptionConstant.USER_NOT_FOUND_BY_USERNAME;
import static com.usb.pss.ipaservice.common.constants.ExceptionConstant.USER_NOT_FOUND_BY_USERNAME_OR_EMAIL;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModuleService moduleService;
    private final GroupRepository groupRepository;
    private final ActionService actionService;
    private final DepartmentService departmentService;
    private final DesignationService designationService;
    private final CurrencyService currencyService;
    private final PointOfSalesService pointOfSalesService;
    private final UserTypeService userTypeService;
    private final AirportService airportService;

    public void createNewUser(RegistrationRequest request) {
        if (!request.password().equals(request.confirmPassword())) {
            throw new RuleViolationException(PASSWORD_CONFIRM_PASSWORD_NOT_MATCH);
        }

        if (userRepository.existsByUsername(request.username())) {
            throw new RuleViolationException(DUPLICATE_USERNAME);
        }
        PointOfSale pointOfSale = pointOfSalesService.findPointOfSaleById(request.pointOfSaleId());
        Set<Currency> currencies = new HashSet<>(currencyService.findAllCurrenciesByIds(request.currencyIds()));
        Set<Airport> airports = new HashSet<>(getAirportsFromIds(request.airportIds()));

        User user = User
            .builder()
            .email(request.personalInfoRequest().email())
            .username(request.username())
            .password(passwordEncoder.encode(request.password()))
            .userCode(request.userCode())
            .userType(userTypeService.findUserTypeById(request.userTypeId()))
            .allowedCurrencies(currencies)
            .pointOfSale(pointOfSale)
            .accessLevel(request.accessLevel())
            .airports(airports)
            .active(true)
            .is2faEnabled(request.is2faEnabled())
            .passwordExpiryDate(LocalDateTime.now())
            .build();

        Department department = departmentService.findById(request.personalInfoRequest().departmentId());
        Designation designation = designationService.findDesignationById(request.personalInfoRequest().designationId());


        var userPersonalInfo = PersonalInfo
            .builder()
            .firstName(request.personalInfoRequest().firstName())
            .lastName(request.personalInfoRequest().lastName())
            .emailOfficial(request.personalInfoRequest().email())
            .emailOther(request.personalInfoRequest().emailOther())
            .telephoneNumber(request.personalInfoRequest().telephoneNumber())
            .mobileNumber(request.personalInfoRequest().mobileNumber())
            .department(department)
            .designation(designation)
            .build();
        user.setPersonalInfo(userPersonalInfo);
        userRepository.save(user);
    }


    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
            () -> new ResourceNotFoundException(USER_NOT_FOUND_BY_ID)
        );
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(
            () -> new ResourceNotFoundException(USER_NOT_FOUND_BY_USERNAME)
        );
    }

    public List<UserGroupResponse> getAllUserWithGroupInfo() {

        return userRepository.findAll()
            .stream()
            .map(user -> {
                UserGroupResponse userGroupResponse = new UserGroupResponse();
                prepareUserWithGroupResponse(user, userGroupResponse);
                return userGroupResponse;
            })
            .toList();
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAllWithPointOfSaleByIdIsNotNull()
            .stream()
            .map(this::prepareUserResponse)
            .toList();
    }


    private void prepareUserWithGroupResponse(User user, UserGroupResponse userGroupResponse) {
        userGroupResponse.setId(user.getId());
        userGroupResponse.setName(user.getUsername());
        if (Objects.nonNull(user.getGroup())) {
            userGroupResponse.setGroupId(user.getGroup().getId());
            userGroupResponse.setGroupName(user.getGroup().getName());
        }
    }

    private UserResponse prepareUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUserName(user.getUsername());
        if (Objects.nonNull(user.getGroup())) {
            userResponse.setUserGroup(user.getGroup().getName());
        } else {
            userResponse.setUserGroup("Not Assigned yet.");
        }
        userResponse.setEmail(user.getEmail());
        userResponse.setPointOfSale(user.getPointOfSale().getName());
        userResponse.setAccessLevel(user.getAccessLevel().name());
        userResponse.setStatus(user.isActive());
        return userResponse;
    }

    @Override
    public void addAdditionalAction(UserActionRequest userActionRequest) {
        User user = userRepository.findUserFetchAdditionalActionsById(userActionRequest.userId())
            .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_BY_ID));

        List<Action> actions = actionService.getActionsByIds(userActionRequest.actionIds());

        user.getAdditionalActions().addAll(actions);
        userRepository.save(user);
    }


    @Override
    public List<ModuleActionResponse> getModuleWiseUserActions(Long userId) {
        return moduleService.getModuleWiseUserActions(userId);
    }

    @Override
    public void updateUserGroup(UserGroupRequest userGroupRequest) {
        User user = findUserById(userGroupRequest.userId());
        Group group = findGroupById(userGroupRequest.groupId());

        user.setGroup(group);
        userRepository.save(user);
    }

    @Override
    public void updateUserStatusInfo(UserStatusRequest request) {
        User user = findUserById(request.userId());

        user.setActive(request.userStatus());
        userRepository.save(user);
    }

    @Override
    public Set<MenuActionResponse> getUserAllPermittedMenu() {
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
    public void changeUserPassword(ChangePasswordRequest request) {
        Optional<User> optionalUser = LoggedUserHelper.getCurrentUser();
        optionalUser.ifPresent(user -> {
            if ((passwordEncoder.matches(request.currentPassword(), user.getPassword()))) {
                if (request.newPassword().equals(request.confirmPassword())) {
                    user.setPassword(passwordEncoder.encode(request.newPassword()));
                    userRepository.save(user);
                } else {
                    throw new RuleViolationException(PASSWORD_CONFIRM_PASSWORD_NOT_MATCH);
                }

            } else {
                throw new RuleViolationException(CURRENT_PASSWORD_NOT_MATCH);
            }
        });

    }

    @Override
    public void updateUserPersonalInfo(UpdateUserInfoRequest updateUserInfoRequest) {
        User user = getUserWithPersonalInfoById(updateUserInfoRequest.id());
        user.set2faEnabled(updateUserInfoRequest.is2faEnabled());
        PersonalInfo personalInfo = user.getPersonalInfo();
        personalInfo.setEmailOfficial(updateUserInfoRequest.emailOfficial());
        personalInfo.setEmailOther(updateUserInfoRequest.emailOther());
        personalInfo.setMobileNumber(updateUserInfoRequest.mobileNumber());
        personalInfo.setTelephoneNumber(updateUserInfoRequest.telephoneNumber());
        userRepository.save(user);
    }


    private List<Airport> getAirportsFromIds(Set<Long> airportIds) {
        return airportService.findAllAirportsByIds(airportIds);
    }

    private Group findGroupById(Long groupId) {
        return groupRepository.findById(groupId)
            .orElseThrow(() -> new ResourceNotFoundException(GROUP_NOT_FOUND));
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_BY_ID));
    }

    @Override
    public UserPersonalInfoResponse getUserPersonalInfo(Long userId) {
        User user = getUserWithPersonalInfoById(userId);
        PersonalInfo personalInfo = user.getPersonalInfo();
        PersonalInfoResponse personalInfoResponse = PersonalInfoResponse.builder()
            .firstName(personalInfo.getFirstName())
            .lastName(personalInfo.getLastName())
            .emailOfficial(personalInfo.getEmailOfficial())
            .emailOther(personalInfo.getEmailOther())
            .department(departmentService.findDepartmentById(personalInfo.getDepartment().getId()))
            .designation(designationService.getDesignationById(personalInfo.getDesignation().getId()))
            .mobileNumber(personalInfo.getMobileNumber())
            .telephoneNumber(personalInfo.getTelephoneNumber())
            .build();
        return UserPersonalInfoResponse.builder()
            .id(user.getId())
            .userName(user.getUsername())
            .personalInfoResponse(personalInfoResponse)
            .userCode(user.getUserCode())
            .userType(userTypeService.getUserType(user.getUserType().getId()))
            .is2faEnabled(user.is2faEnabled())
            .accessLevel(user.getAccessLevel())
            .pointOfSale(pointOfSalesService.getPointOfSales(user.getPointOfSale().getId()))
            .airports(airportService.getAirportResponsesFromAirports(user.getAirports()))
            .allowedCurrencies(currencyService.getAllCurrencyResponsesFromCurrencies(user.getAllowedCurrencies()))
            .build();
    }


    private User getUserWithPersonalInfoById(Long userId) {
        return userRepository.findUserWithAllInfoById(userId)
            .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_BY_ID));
    }


    @Override
    public User findUserByUsernameOrEmail(String usernameOrEmail) {
        return userRepository
            .findUserByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
            .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_BY_USERNAME_OR_EMAIL));
    }
}
