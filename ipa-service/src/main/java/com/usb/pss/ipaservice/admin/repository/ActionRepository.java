package com.usb.pss.ipaservice.admin.repository;

import com.usb.pss.ipaservice.admin.model.entity.Action;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ActionRepository extends JpaRepository<Action, Long> {
    @EntityGraph(attributePaths = {"menu"})
    List<Action> findActionAndFetchMenuByIdIn(Collection<Long> ids);

}
