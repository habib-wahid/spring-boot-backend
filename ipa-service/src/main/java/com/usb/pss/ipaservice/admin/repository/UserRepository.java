package com.usb.pss.ipaservice.admin.repository;


import com.usb.pss.ipaservice.admin.model.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    Optional<User> findUserByUsername(String username);

    @EntityGraph(attributePaths = {"role.permittedActions", "additionalActions"})
    Optional<User> findUserAndFetchActionByUsername(String username);

//    @EntityGraph(attributePaths = {"additionalActions"})
//    Optional<User> findUserFetchRoleAndAdditionalActionsById(Long userId);

    @EntityGraph(attributePaths = {"permittedMenus", "permittedActions"})
    Optional<User> findUserWithMenusAndActionsById(Long userId);

    @EntityGraph(attributePaths = {"additionalActions"})
    Optional<User> findUserFetchAdditionalActionsById(Long userId);

    @EntityGraph(attributePaths = {"roles", "permittedMenus", "permittedActions"})
    Optional<User> findUserWithRolesMenusAndActionsById(Long userId);

}
