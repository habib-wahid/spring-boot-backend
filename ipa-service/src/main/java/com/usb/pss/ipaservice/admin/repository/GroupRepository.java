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

    @Query("select g from Group g where (:name is null or g.name ilike %:name%) and" +
        "(:description is null or g.description ilike %:description%) and" +
        "(:activeStatus is null or g.active = :activeStatus) and" +
        "(:createdBy is null or g.createdBy  = :createdBy) and" +
        "((cast(:startCreatedDate as localdatetime ) is null or cast(:endCreatedDate as localdatetime )is null)" +
        " or (g.createdDate between :startCreatedDate and :endCreatedDate))")
    Page<Group> searchGroupByFilteringCriteria(
        String name,
        String description,
        boolean activeStatus,
        Long createdBy,
        LocalDateTime startCreatedDate,
        LocalDateTime endCreatedDate,
        Pageable pageable
    );

}
