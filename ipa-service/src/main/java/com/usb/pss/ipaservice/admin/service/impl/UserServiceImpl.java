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
import com.usb.pss.ipaservice.admin.dto.response.MenuResponse;
import com.usb.pss.ipaservice.admin.dto.response.ModuleResponse;
import com.usb.pss.ipaservice.admin.dto.response.PointOfSaleResponse;
import com.usb.pss.ipaservice.admin.dto.response.UserPersonalInfoResponse;
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
            .active(true)
            .build();
        Department department = findDepartmentById(request.departmentId());
        Designation designation = findDesignationById(request.designationId());
        Set<PointOfSale> pointOfSales = new HashSet<>(getPointOfSalesFromIds(request.pointOfSaleIds()));
        Set<Currency> currencies = new HashSet<>(getCurrenciesFromIds(request.currencyIds()));
        var userPersonalInfo = PersonalInfo
            .builder()
            .firstName(request.firstName())
            .lastName(request.lastName())
            .emailOfficial(request.email())
            .department(department)
            .designation(designation)
            .allowedCurrencies(currencies)
            .pointOfSales(pointOfSales)
            .build();
        user.setPersonalInfo(userPersonalInfo);
        userRepository.save(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAllUsersWithGroupByIdIsNotNull().stream().filter(Objects::nonNull).map(user -> {
            UserResponse userResponse = new UserResponse();
            prepareResponse(user, userResponse);
            return userResponse;
        }).toList();
    }

    private void prepareResponse(User user, UserResponse userResponse) {
        userResponse.setId(user.getId());
        userResponse.setName(user.getUsername());
        userResponse.setGroupId(user.getGroup().getId());
        userResponse.setGroupName(user.getGroup().getName());

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
    public List<ModuleResponse> getModuleWiseUserActions(Long userId) {
        return moduleService.getModuleWiseUserActions(userId);
    }

    @Override
    public void updateUserGroup(UserGroupRequest userGroupRequest) {
        User user = userRepository.findById(userGroupRequest.userId())
            .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_BY_ID));

        Group group = groupRepository.findById(userGroupRequest.groupId())
            .orElseThrow(() -> new ResourceNotFoundException(GROUP_NOT_FOUND));

        user.setGroup(group);
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
        PersonalInfo personalInfo = user.getPersonalInfo();
        personalInfo.setFirstName(updateUserInfoRequest.firstName());
        personalInfo.setLastName(updateUserInfoRequest.lastName());
        Department department = findDepartmentById(updateUserInfoRequest.departmentId());
        Designation designation = findDesignationById(updateUserInfoRequest.designationId());
        personalInfo.setDepartment(department);
        personalInfo.setDesignation(designation);
        personalInfo.setEmailOfficial(updateUserInfoRequest.emailOfficial());
        personalInfo.setEmailOther(updateUserInfoRequest.emailOptional());
        personalInfo.setMobileNumber(updateUserInfoRequest.mobileNumber());
        personalInfo.setTelephoneNumber(updateUserInfoRequest.telephoneNumber());
        personalInfo.setAccessLevel(updateUserInfoRequest.accessLevel());
        Set<Currency> currencies = personalInfo.getAllowedCurrencies();
        List<Currency> updatedCurrencies = getCurrenciesFromIds(updateUserInfoRequest.allowedCurrencyIds());
        currencies.retainAll(updatedCurrencies);
        currencies.addAll(updatedCurrencies);
        Set<PointOfSale> pointOfSales = personalInfo.getPointOfSales();
        List<PointOfSale> updatedPointOfSales = getPointOfSalesFromIds(updateUserInfoRequest.pointOfSaleIds());
        pointOfSales.retainAll(updatedPointOfSales);
        pointOfSales.addAll(updatedPointOfSales);
        userRepository.save(user);
    }

    private List<PointOfSale> getPointOfSalesFromIds(Set<Long> pointOfSaleIds) {
        return pointOfSaleRepository.findByIdIn(pointOfSaleIds);
    }

    private List<Currency> getCurrenciesFromIds(Set<Long> currencyIds) {
        return currencyRepository.findByIdIn(currencyIds);
    }

    @Override
    public UserPersonalInfoResponse getUserPersonalInfo(Long userId) {
        User user = getUserWithPersonalInfoById(userId);
        PersonalInfo personalInfo = user.getPersonalInfo();
        return UserPersonalInfoResponse.builder()
            .firstName(personalInfo.getFirstName())
            .lastName(personalInfo.getLastName())
            .emailOfficial(personalInfo.getEmailOfficial())
            .emailOther(personalInfo.getEmailOther())
            .departmentResponse(getDepartmentResponse(personalInfo.getDepartment()))
            .designationResponse(getDesignationResponse(personalInfo.getDesignation()))
            .mobileNumber(personalInfo.getMobileNumber())
            .telephoneNumber(personalInfo.getTelephoneNumber())
            .accessLevel(personalInfo.getAccessLevel())
            .pointOfSales(getPointOfSalesResponses(personalInfo.getPointOfSales()))
            .allowedCurrencies(getCurrencyResponsesFromCurrencies(personalInfo.getAllowedCurrencies()))
            .build();
    }

    private DesignationResponse getDesignationResponse(Designation designation) {
        DesignationResponse designationResponse = new DesignationResponse();
        designationResponse.setId(designation.getId());
        designationResponse.setDesignationName(designation.getName());
        return designationResponse;
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

    private List<PointOfSaleResponse> getPointOfSalesResponses(Collection<PointOfSale> pointOfSales) {
        return pointOfSales.stream()
            .map(this::getPointOfSaleResponseFromPointOfSale)
            .toList();
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
