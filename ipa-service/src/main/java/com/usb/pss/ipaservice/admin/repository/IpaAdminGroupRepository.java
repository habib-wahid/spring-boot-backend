package com.usb.pss.ipaservice.admin.repository;

import com.usb.pss.ipaservice.admin.model.entity.IpaAdminGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IpaAdminGroupRepository extends JpaRepository<IpaAdminGroup, Long> {
    @Query("select gr from IpaAdminGroup gr where gr.id = :groupId and gr.active = true")
    Optional<IpaAdminGroup> findActiveGroupById(Long groupId);

    @Query("select gr from IpaAdminGroup gr where gr.name = :groupName and gr.active = true")
    Optional<IpaAdminGroup> findActiveGroupByName(String groupName);

    @Query("select gr from IpaAdminGroup gr where gr.active = true order by gr.name asc")
    List<IpaAdminGroup> findAllActiveGroups();

    @Query("select gr from IpaAdminGroup gr where gr.active = false order by gr.name asc")
    List<IpaAdminGroup> findAllInactiveGroups();
}
