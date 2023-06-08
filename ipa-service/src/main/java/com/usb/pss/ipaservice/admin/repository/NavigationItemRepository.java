package com.usb.pss.ipaservice.admin.repository;

import com.usb.pss.ipaservice.admin.model.entity.NavigationItem;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NavigationItemRepository extends JpaRepository<NavigationItem, Long> {


    @Query(
            nativeQuery = true,
            value = "select ni.* from navigation_item ni " +
                    "inner join permission p on ni.permission_id = p.id " +
                    "where p.username=:userName"
    )
    List<NavigationItem> getByUserPermission(
            @Param("userName") String userName
    );
}
