package com.usb.pss.ipaservice.admin.repository;


import com.usb.pss.ipaservice.admin.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findUserByUsername(String username);

    @EntityGraph(attributePaths = {"personalInfo"})
    Optional<User> findUserByUsernameOrEmail(String username, String email);

    @EntityGraph(attributePaths = {"group.permittedActions", "additionalActions"})
    Optional<User> findUserAndFetchActionByUsername(String username);

    @EntityGraph(attributePaths = {"group"})
    List<User> findAllWithGroupByIdIsNotNull();

    @EntityGraph(attributePaths = {"group.permittedActions", "additionalActions", "personalInfo"})
    Optional<User> findUserAndFetchActionAndPersonalInfoByUsername(String username);

    @EntityGraph(attributePaths = {"personalInfo", "personalInfo.department", "personalInfo.designation",
            "airports", "userType", "pointOfSale", "allowedCurrencies"})
    Optional<User> findUserWithAllInfoById(Long userId);

    @EntityGraph(attributePaths = {"pointOfSale", "group", "accessLevels"})
    Page<User> findAllWithPointOfSaleAndGroupAndAccessLevelByIdIsNotNull(Pageable pageable);

    @EntityGraph(attributePaths = {"additionalActions"})
    Optional<User> findUserFetchAdditionalActionsById(Long userId);
    @EntityGraph(attributePaths = {"additionalActions"})
    Optional<User> findUserFetchAdditionalActionsByUsername(String username);

    @EntityGraph(attributePaths = {"additionalActions", "group.permittedActions"})
    Optional<User> findUserFetchAdditionalActionsAndGroupActionsById(Long userId);
}
