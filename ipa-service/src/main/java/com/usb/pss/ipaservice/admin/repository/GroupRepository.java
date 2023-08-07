package com.usb.pss.ipaservice.admin.repository;

import com.usb.pss.ipaservice.admin.model.entity.Group;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.Query;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByNameIgnoreCase(String groupName);

    List<Group> findAllByOrderByCreatedDateDesc();

    @EntityGraph(attributePaths = {"permittedActions"})
    Optional<Group> findGroupAndFetchActionsById(Long groupId);

    @EntityGraph(attributePaths = {"permittedActions", "permittedActions.menu"})
    List<Group> findAllGroupAndMenuAndActionByIdIn(Set<Long> groups);

    @Query("select g from Group g where (:searchByName = false or g.name ilike %:name%) and" +
        "(:searchByDescription = false or g.description ilike %:description%) and" +
        "(:searchByActiveStatus = false or g.active = :activeStatus) and" +
        "(:searchByCreatedBy = false or g.createdBy  = :createdBy) and" +
        "(:searchByCreatedDate = false or (g.createdDate >= :startCreatedDate and g.createdDate < :endCreatedDate))")
    Page<Group> searchGroupByFilteringCriteria(
        boolean searchByName, String name,
        boolean searchByDescription, String description,
        boolean searchByActiveStatus, boolean activeStatus,
        boolean searchByCreatedBy, Long createdBy,
        boolean searchByCreatedDate,
        LocalDateTime startCreatedDate,
        LocalDateTime endCreatedDate,
        Pageable pageable
    );

}
