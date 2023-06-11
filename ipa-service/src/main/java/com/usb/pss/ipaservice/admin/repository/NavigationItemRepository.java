package com.usb.pss.ipaservice.admin.repository;

import com.usb.pss.ipaservice.admin.model.entity.NavigationItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NavigationItemRepository extends JpaRepository<NavigationItem, Long> {


    @Query(
            "select ni from NavigationItem ni inner join Permission p on ni.permissionId = p.id" +
                    " where p.username = :user_name"
    )
    List<NavigationItem> getByUserPermission(
            @Param("user_name") String userName
    );
}
