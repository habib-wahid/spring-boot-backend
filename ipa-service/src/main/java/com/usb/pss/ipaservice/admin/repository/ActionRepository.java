package com.usb.pss.ipaservice.admin.repository;

import com.usb.pss.ipaservice.admin.model.entity.Action;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface ActionRepository extends CrudRepository<Action, Long> {
    @EntityGraph(attributePaths = {"menu"})
    List<Action> findActionFetchMenuAllByIdIn(Collection<Long> ids);

}
