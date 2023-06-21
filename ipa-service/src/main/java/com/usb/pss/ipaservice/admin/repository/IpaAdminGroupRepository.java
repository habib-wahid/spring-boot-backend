package com.usb.pss.ipaservice.admin.repository;

import com.usb.pss.ipaservice.admin.dto.response.GroupResponse;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IpaAdminGroupRepository extends JpaRepository<IpaAdminGroup, Long> {
    Optional<IpaAdminGroup> findByNameIgnoreCase(String groupName);

    List<IpaAdminGroup> findAllByOrderByCreatedAtDesc();

//    @Query("select new com.usb.pss.ipaservice.admin.dto.response.GroupResponse(" +
//            "gr.id, " +
//            "gr.name) " +
//            "from IpaAdminGroup gr")
//    List<GroupResponse> findAllGroupResponse();
}
