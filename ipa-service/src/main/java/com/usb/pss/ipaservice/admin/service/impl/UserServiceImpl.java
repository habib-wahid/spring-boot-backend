package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.request.ChangePasswordRequest;
import com.usb.pss.ipaservice.admin.dto.request.RegistrationRequest;
import com.usb.pss.ipaservice.admin.dto.request.UpdateUserInfoRequest;
import com.usb.pss.ipaservice.admin.dto.request.UserActionRequest;
import com.usb.pss.ipaservice.admin.dto.request.UserGroupRequest;
import com.usb.pss.ipaservice.admin.dto.request.UserStatusRequest;
import com.usb.pss.ipaservice.admin.dto.response.CurrencyResponse;
import com.usb.pss.ipaservice.admin.dto.response.DepartmentResponse;
import com.usb.pss.ipaservice.admin.dto.response.DesignationResponse;
import com.usb.pss.ipaservice.admin.dto.response.MenuActionResponse;
import com.usb.pss.ipaservice.admin.dto.response.ModuleActionResponse;
import com.usb.pss.ipaservice.admin.dto.response.PointOfSaleResponse;
import com.usb.pss.ipaservice.admin.dto.response.UserPersonalInfoResponse;
import com.usb.pss.ipaservice.admin.dto.response.UserGroupResponse;
import com.usb.pss.ipaservice.admin.dto.response.UserResponse;
import com.usb.pss.ipaservice.admin.model.entity.Action;
import com.usb.pss.ipaservice.admin.model.entity.Currency;
import com.usb.pss.ipaservice.admin.model.entity.Department;
import com.usb.pss.ipaservice.admin.model.entity.Designation;
import com.usb.pss.ipaservice.admin.model.entity.Group;
import com.usb.pss.ipaservice.admin.model.entity.PersonalInfo;
import com.usb.pss.ipaservice.admin.model.entity.PointOfSale;
import com.usb.pss.ipaservice.admin.model.entity.User;
import com.usb.pss.ipaservice.admin.repository.ActionRepository;
import com.usb.pss.ipaservice.admin.repository.CurrencyRepository;
import com.usb.pss.ipaservice.admin.repository.DepartmentRepository;
import com.usb.pss.ipaservice.admin.repository.DesignationRepository;
import com.usb.pss.ipaservice.admin.repository.GroupRepository;
import com.usb.pss.ipaservice.admin.repository.PointOfSaleRepository;
import com.usb.pss.ipaservice.admin.repository.UserRepository;
import com.usb.pss.ipaservice.admin.service.iservice.ModuleService;
import com.usb.pss.ipaservice.admin.service.iservice.UserService;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import com.usb.pss.ipaservice.exception.RuleViolationException;
import com.usb.pss.ipaservice.utils.LoggedUserHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;


import static com.usb.pss.ipaservice.common.ExceptionConstant.CURRENT_PASSWORD_NOT_MATCH;
import static com.usb.pss.ipaservice.common.ExceptionConstant.DEPARTMENT_NOT_FOUND;
import static com.usb.pss.ipaservice.common.ExceptionConstant.DESIGNATION_NOT_FOUND;
import static com.usb.pss.ipaservice.common.ExceptionConstant.DUPLICATE_USERNAME;
import static com.usb.pss.ipaservice.common.ExceptionConstant.GROUP_NOT_FOUND;
import static com.usb.pss.ipaservice.common.ExceptionConstant.NEW_PASSWORD_NOT_MATCH;
import static com.usb.pss.ipaservice.common.ExceptionConstant.PASSWORD_NOT_MATCH;
import static com.usb.pss.ipaservice.common.ExceptionConstant.POINT_OF_SALES_NOT_FOUND;
import static com.usb.pss.ipaservice.common.ExceptionConstant.USER_NOT_FOUND_BY_ID;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModuleService moduleService;
    private final GroupRepository groupRepository;
    private final ActionRepository actionRepository;
    private final DepartmentRepository departmentRepository;
    private final DesignationRepository designationRepository;
    private final CurrencyRepository currencyRepository;
    private final PointOfSaleRepository pointOfSaleRepository;

    public void createNewUser(RegistrationRequest request) {
        if (!request.password().equals(request.confirmPassword())) {
            throw new RuleViolationException(PASSWORD_NOT_MATCH);
        }

        if (userRepository.existsByUsername(request.username())) {
            throw new RuleViolationException(DUPLICATE_USERNAME);
        }

        var user = User
                .builder()
                .email(request.email())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .userCode(request.userCode())
                .active(true)
                .is2faEnabled(request.is2faEnabled())
                .build();

        Department department = findDepartmentById(request.departmentId());
        Designation designation = findDesignationById(request.designationId());
        PointOfSale pointOfSale = getPointOfSale(request.pointOfSaleId());
        Set<Currency> currencies = new HashSet<>(getCurrenciesFromIds(request.currencyIds()));

        var userPersonalInfo = PersonalInfo
                .builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .emailOfficial(request.email())
                .telephoneNumber(request.telephoneNumber())
                .mobileNumber(request.mobileNumber())
                .department(department)
                .designation(designation)
                .allowedCurrencies(currencies)
                .pointOfSale(pointOfSale)
                .accessLevel(request.accessLevel())
                .airport(request.airport())
                .build();
        user.setPersonalInfo(userPersonalInfo);
        userRepository.save(user);
    }

    @Override
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
        return userRepository.findAllWithPersonalInfoByIdIsNotNull()
                .stream()
                .map(this::prepareUserResponse)
                .toList();
    }

    @Override
    public List<UserResponse> getAllUsersByFilteredText(String filteredText) {
        return userRepository.findAllUsersByFilteredText(filteredText)
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
        userResponse.setUserName(user.getUsername());
        if (Objects.nonNull(user.getGroup())) {
            userResponse.setUserGroup(user.getGroup().getName());
        } else {
            userResponse.setUserGroup("Not Assigned yet.");
        }
        userResponse.setEmail(user.getEmail());
        userResponse.setPointOfSale(user.getPersonalInfo().getPointOfSale().getName());
        userResponse.setAccessLevel(user.getPersonalInfo().getAccessLevel());
        userResponse.setStatus(user.isActive());
        return userResponse;
    }

    @Override
    public void addAdditionalAction(UserActionRequest userActionRequest) {
        User user = userRepository.findUserFetchAdditionalActionsById(userActionRequest.userId())
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_BY_ID));

        List<Action> actions = actionRepository.findByIdIn(userActionRequest.actionIds());

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
                    throw new RuleViolationException(NEW_PASSWORD_NOT_MATCH);
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
        personalInfo.setEmailOther(updateUserInfoRequest.emailOptional());
        personalInfo.setMobileNumber(updateUserInfoRequest.mobileNumber());
        personalInfo.setTelephoneNumber(updateUserInfoRequest.telephoneNumber());
        userRepository.save(user);
    }

    private PointOfSale getPointOfSale(Long pointOfSaleId) {
        return pointOfSaleRepository.findById(pointOfSaleId)
                .orElseThrow(() -> new ResourceNotFoundException(POINT_OF_SALES_NOT_FOUND));
    }

    private List<Currency> getCurrenciesFromIds(Set<Long> currencyIds) {
        return currencyRepository.findByIdIn(currencyIds);
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
        return UserPersonalInfoResponse.builder()
                .userName(user.getUsername())
                .firstName(personalInfo.getFirstName())
                .lastName(personalInfo.getLastName())
                .userCode(user.getUserCode())
                .is2faEnabled(user.is2faEnabled())
                .emailOfficial(personalInfo.getEmailOfficial())
                .emailOther(personalInfo.getEmailOther())
                .department(getDepartmentResponse(personalInfo.getDepartment()))
                .designation(getDesignationResponse(personalInfo.getDesignation()))
                .mobileNumber(personalInfo.getMobileNumber())
                .telephoneNumber(personalInfo.getTelephoneNumber())
                .accessLevel(personalInfo.getAccessLevel())
                .pointOfSale(getPointOfSaleResponseFromPointOfSale(personalInfo.getPointOfSale()))
                .allowedCurrencies(getCurrencyResponsesFromCurrencies(personalInfo.getAllowedCurrencies()))
                .build();
    }

    private DesignationResponse getDesignationResponse(Designation designation) {
        return new DesignationResponse(designation.getId(), designation.getName());
    }

    private DepartmentResponse getDepartmentResponse(Department department) {
        return new DepartmentResponse(department.getId(), department.getName());
    }

    private List<CurrencyResponse> getCurrencyResponsesFromCurrencies(Collection<Currency> currencies) {
        return currencies.stream()
                .map(this::getCurrencyResponseFromCurrency)
                .toList();
    }

    private CurrencyResponse getCurrencyResponseFromCurrency(Currency currency) {
        CurrencyResponse currencyResponse = new CurrencyResponse();
        currencyResponse.setCode(currency.getCode());
        currencyResponse.setId(currency.getId());
        currencyResponse.setName(currencyResponse.getName());
        return currencyResponse;
    }

    private PointOfSaleResponse getPointOfSaleResponseFromPointOfSale(PointOfSale pointOfSale) {
        return new PointOfSaleResponse(pointOfSale.getId(), pointOfSale.getName());
    }


    private User getUserWithPersonalInfoById(Long userId) {
        return userRepository.findUserWithPersonalInfoById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_BY_ID));
    }


    private Department findDepartmentById(Long departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException(DEPARTMENT_NOT_FOUND));
    }

    private Designation findDesignationById(Long designationId) {
        return designationRepository.findById(designationId)
                .orElseThrow(() -> new ResourceNotFoundException(DESIGNATION_NOT_FOUND));
    }

}
