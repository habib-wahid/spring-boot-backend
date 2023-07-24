package com.usb.pss.ipaservice.admin.repository;

import com.usb.pss.ipaservice.admin.model.entity.Action;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ActionRepository extends JpaRepository<Action, Long> {
    @EntityGraph(attributePaths = {"menu"})
    List<Action> findActionAndFetchMenuByIdIn(Collection<Long> ids);

    List<Action> findByIdIn(Collection<Long> ids);

    @Query("select ac from Action ac left join ac.groups gr where gr.id = :groupId")
    List<Action> findGroupWiseAction(@Param("groupId") Long groupId);

}
