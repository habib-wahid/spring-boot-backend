package com.usb.pss.ipaservice.admin.repository;

import com.usb.pss.ipaservice.admin.model.entity.Group;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByNameIgnoreCase(String groupName);

    List<Group> findAllByOrderByCreatedDateDesc();

    @EntityGraph(attributePaths = {"permittedMenus", "permittedActions"})
    Optional<Group> findGroupAndFetchMenuAndActionsById(Long groupId);


    @EntityGraph(attributePaths = {"permittedActions", "permittedActions.menu"})
    List<Group> findAllGroupAndMenuAndActionByIdIn(Set<Long> groups);

}
