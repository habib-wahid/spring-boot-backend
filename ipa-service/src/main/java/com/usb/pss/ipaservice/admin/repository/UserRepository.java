package com.usb.pss.ipaservice.admin.repository;


import com.usb.pss.ipaservice.admin.model.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    Optional<User> findUserByUsername(String username);

    @EntityGraph(attributePaths = {"group.permittedActions", "additionalActions"})
    Optional<User> findUserAndFetchActionByUsername(String username);

    @EntityGraph(attributePaths = {"personalInfo", "personalInfo.department", "personalInfo.designation",
            "personalInfo.allowedCurrencies"})
    Optional<User> findUserWithPersonalInfoById(Long userId);

    @EntityGraph(attributePaths = {"personalInfo"})
    List<User> findAllWithPersonalInfoByIdIsNotNull();

    @EntityGraph(attributePaths = {"additionalActions"})
    Optional<User> findUserFetchAdditionalActionsById(Long userId);

    @EntityGraph(attributePaths = {"additionalActions"})
    Optional<User> findUserFetchAdditionalActionsByUsername(String username);

    @EntityGraph(attributePaths = {"personalInfo", "personalInfo.pointOfSale", "group"})
    @Query("select user from User user where user.username like '%' || :filteredText || '%' " +
            "or user.group.name like :filteredText or user.email like '%' || :filteredText || '%' " +
            "or user.personalInfo.pointOfSale.name like '%' || :filteredText || '%'")
    List<User> findAllUsersByFilteredText(@Param("filteredText") String filteredText);

}
