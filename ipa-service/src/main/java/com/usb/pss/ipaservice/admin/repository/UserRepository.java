package com.usb.pss.ipaservice.admin.repository;


import com.usb.pss.ipaservice.admin.model.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    Optional<User> findUserByUsername(String username);

    @EntityGraph(attributePaths = {"group.permittedActions", "additionalActions"})
    Optional<User> findUserAndFetchActionByUsername(String username);

    @EntityGraph(attributePaths = {"personalInfo", "personalInfo.department", "personalInfo.designation",
        "personalInfo.allowedCurrencies"})
    Optional<User> findUserWithPersonalInfoById(Long userId);

    @EntityGraph(attributePaths = {"additionalActions"})
    Optional<User> findUserFetchAdditionalActionsById(Long userId);

    @EntityGraph(attributePaths = {"additionalActions"})
    Optional<User> findUserFetchAdditionalActionsByUsername(String username);

}
