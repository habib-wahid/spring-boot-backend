package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.PaginationResponse;
import com.usb.pss.ipaservice.admin.dto.request.ChangePasswordRequest;
import com.usb.pss.ipaservice.admin.dto.request.RegistrationRequest;
import com.usb.pss.ipaservice.admin.dto.request.UpdateUserInfoRequest;
import com.usb.pss.ipaservice.admin.dto.request.UserActionRequest;
import com.usb.pss.ipaservice.admin.dto.request.UserGroupRequest;
import com.usb.pss.ipaservice.admin.dto.request.UserStatusRequest;
import com.usb.pss.ipaservice.admin.dto.response.PersonalInfoResponse;
import com.usb.pss.ipaservice.admin.model.entity.AccessLevel;
import com.usb.pss.ipaservice.admin.service.iservice.AccessLevelService;
import com.usb.pss.ipaservice.admin.service.iservice.ActionService;
import com.usb.pss.ipaservice.admin.service.iservice.AirportService;
import com.usb.pss.ipaservice.admin.service.iservice.CurrencyService;
import com.usb.pss.ipaservice.admin.service.iservice.DepartmentService;
import com.usb.pss.ipaservice.admin.service.iservice.DesignationService;
import com.usb.pss.ipaservice.admin.service.iservice.GroupService;
import com.usb.pss.ipaservice.admin.service.iservice.PointOfSalesService;
import com.usb.pss.ipaservice.admin.service.iservice.UserTypeService;
import com.usb.pss.ipaservice.admin.dto.response.ModuleActionResponse;
import com.usb.pss.ipaservice.admin.dto.response.UserGroupResponse;
import com.usb.pss.ipaservice.admin.dto.response.UserProfileResponse;
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
import com.usb.pss.ipaservice.admin.repository.UserRepository;
import com.usb.pss.ipaservice.admin.service.iservice.ModuleService;
import com.usb.pss.ipaservice.admin.service.iservice.UserService;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import com.usb.pss.ipaservice.exception.RuleViolationException;
import com.usb.pss.ipaservice.utils.LoggedUserHelper;
import lombok.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static com.usb.pss.ipaservice.common.ApplicationConstants.DEFAULT_DIRECTION;
import static com.usb.pss.ipaservice.common.ApplicationConstants.DEFAULT_SORT_BY;
import static com.usb.pss.ipaservice.common.constants.ExceptionConstant.CURRENT_PASSWORD_NOT_MATCH;
import static com.usb.pss.ipaservice.common.constants.ExceptionConstant.DUPLICATE_EMAIL;
import static com.usb.pss.ipaservice.common.constants.ExceptionConstant.DUPLICATE_USERNAME;
import static com.usb.pss.ipaservice.common.constants.ExceptionConstant.PASSWORD_CONFIRM_PASSWORD_NOT_MATCH;
import static com.usb.pss.ipaservice.common.constants.ExceptionConstant.USER_NOT_FOUND_BY_ID;
import static com.usb.pss.ipaservice.common.constants.ExceptionConstant.USER_NOT_FOUND_BY_USERNAME;
import static com.usb.pss.ipaservice.common.constants.ExceptionConstant.USER_NOT_FOUND_BY_USERNAME_OR_EMAIL;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Lazy
    private final ModuleService moduleService;
    private final GroupService groupService;
    private final ActionService actionService;
    private final DepartmentService departmentService;
    private final DesignationService designationService;
    private final CurrencyService currencyService;
    private final PointOfSalesService pointOfSalesService;
    private final UserTypeService userTypeService;
    private final AirportService airportService;
    private final AccessLevelService accessLevelService;

    public void createNewUser(RegistrationRequest request) {
        if (!request.password().equals(request.confirmPassword())) {
            throw new RuleViolationException(PASSWORD_CONFIRM_PASSWORD_NOT_MATCH);
        }

        if (userRepository.existsByUsername(request.username())) {
            throw new RuleViolationException(DUPLICATE_USERNAME);
        }

        if (userRepository.existsByEmail(request.personalInfoRequest().email())) {
            throw new RuleViolationException(DUPLICATE_EMAIL);
        }

        PointOfSale pointOfSale = pointOfSalesService.findPointOfSaleById(request.pointOfSaleId());
        Set<Currency> currencies = new HashSet<>(currencyService.findAllCurrenciesByIds(request.currencyIds()));
        Set<Airport> airports = new HashSet<>(airportService.findAllAirportsByIds(request.airportIds()));
        Set<AccessLevel> accessLevels =
                new HashSet<>(accessLevelService.findAccessLevelsByIds(request.accessLevelIds()));

        User user = User
                .builder()
                .email(request.personalInfoRequest().email())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .userCode(request.userCode())
                .userType(userTypeService.findUserTypeById(request.userTypeId()))
                .allowedCurrencies(currencies)
                .pointOfSale(pointOfSale)
                .accessLevels(accessLevels)
                .airports(airports)
                .active(true)
                .is2faEnabled(request.is2faEnabled())
                .passwordExpiryDate(LocalDateTime.now())
                .build();

        Department department = departmentService.findDepartmentById(request.personalInfoRequest().departmentId());
        Designation designation = designationService.findDesignationById(request.personalInfoRequest().designationId());


        PersonalInfo userPersonalInfo = PersonalInfo
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

        return userRepository.findAllWithGroupByIdIsNotNull()
                .stream()
                .map(this::getUserGroupResponse)
                .toList();
    }

    @Override
    public PaginationResponse<UserResponse> getAllUsers(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(DEFAULT_DIRECTION, DEFAULT_SORT_BY));
        Page<User> userPage = userRepository.findAllWithPointOfSaleAndGroupAndAccessLevelByIdIsNotNull(pageable);
        return new PaginationResponse<>(
                userPage.getPageable().getPageNumber(),
                userPage.getPageable().getPageSize(),
                userPage.getTotalElements(),
                userPage.getContent()
                        .stream()
                        .map(this::prepareUserResponse)
                        .toList(),
                Map.of(
                        "userName", "User Name",
                        "group", "Group Name",
                        "email", "Email",
                        "pointOfSale", "Point of Sale",
                        "accessLevels", "Access Level",
                        "status", "Status"
                )
        );
    }


    private UserGroupResponse getUserGroupResponse(User user) {
        UserGroupResponse userGroupResponse = new UserGroupResponse();
        userGroupResponse.setId(user.getId());
        userGroupResponse.setName(user.getUsername());
        if (Objects.nonNull(user.getGroup())) {
            userGroupResponse.setGroupId(user.getGroup().getId());
            userGroupResponse.setGroupName(user.getGroup().getName());
        }
        return userGroupResponse;
    }

    private UserResponse prepareUserResponse(User user) {
        String accessLevels = String
                .join(", ", user
                        .getAccessLevels()
                        .stream()
                        .map(AccessLevel::getName)
                        .toList());
        UserResponse userResponse = UserResponse
                .builder()
                .id(user.getId())
                .userName(user.getUsername())
                .email(user.getEmail())
                .accessLevels(accessLevels)
                .pointOfSale(user.getPointOfSale().getName())
                .status(user.isActive())
                .build();
        userResponse.setGroup(Objects.nonNull(user.getGroup()) ? user.getGroup().getName() : "");
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
        Group group = groupService.findGroupById(userGroupRequest.groupId());

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
        User user = getUserWithAllInfoById(updateUserInfoRequest.id());
        user.set2faEnabled(updateUserInfoRequest.is2faEnabled());
        PersonalInfo personalInfo = user.getPersonalInfo();
        personalInfo.setEmailOfficial(updateUserInfoRequest.emailOfficial());
        personalInfo.setEmailOther(updateUserInfoRequest.emailOther());
        personalInfo.setMobileNumber(updateUserInfoRequest.mobileNumber());
        personalInfo.setTelephoneNumber(updateUserInfoRequest.telephoneNumber());
        userRepository.save(user);
    }


    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_BY_ID));
    }

    @Override
    public UserProfileResponse getUserPersonalInfo(Long userId) {
        User user = getUserWithAllInfoById(userId);
        PersonalInfo personalInfo = user.getPersonalInfo();
        PersonalInfoResponse personalInfoResponse = PersonalInfoResponse.builder()
                .firstName(personalInfo.getFirstName())
                .lastName(personalInfo.getLastName())
                .emailOfficial(personalInfo.getEmailOfficial())
                .emailOther(personalInfo.getEmailOther())
                .department(departmentService.getDepartmentResponse(personalInfo.getDepartment()))
                .designation(designationService.getDesignationResponse(personalInfo.getDesignation()))
                .mobileNumber(personalInfo.getMobileNumber())
                .telephoneNumber(personalInfo.getTelephoneNumber())
                .build();
        return UserProfileResponse.builder()
                .id(user.getId())
                .userCode(user.getUserCode())
                .userName(user.getUsername())
                .personalInfoResponse(personalInfoResponse)
                .accessLevels(accessLevelService.getAccessLevelResponses(user.getAccessLevels()))
                .is2faEnabled(user.is2faEnabled())
                .userType(userTypeService.getUserTypeResponse(user.getUserType()))
                .pointOfSale(pointOfSalesService.getPointOfSaleResponse(user.getPointOfSale()))
                .allowedCurrencies(currencyService.getAllCurrencyResponses(user.getAllowedCurrencies()))
                .airports(airportService.getAirportResponses(user.getAirports()))
                .build();
    }


    private User getUserWithAllInfoById(Long userId) {
        return userRepository.findUserWithAllInfoById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_BY_ID));
    }


    @Override
    public User findUserByUsernameOrEmail(String usernameOrEmail) {
        return userRepository
                .findUserByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_BY_USERNAME_OR_EMAIL));
    }

    public UserDto getUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getUsername());
        userDto.setUserCode(userDto.getUserCode());
        return userDto;
    }
}

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
class UserDto {
    private Long id;
    private String name;
    private String userCode;
}