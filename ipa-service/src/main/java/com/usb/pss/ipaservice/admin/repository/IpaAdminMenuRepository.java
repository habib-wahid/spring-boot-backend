package com.usb.pss.ipaservice.admin.repository;

import com.usb.pss.ipaservice.admin.model.entity.IpaAdminMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IpaAdminMenuRepository extends JpaRepository<IpaAdminMenu, Long> {


//    @Query(
//            "select ni from NavigationItem ni inner join IpaAdminMenu p on ni.permissionId = p.id" +
//                    " where p.username = :user_name"
//    )
//    List<NavigationItem> getByUserPermission(
//            @Param("user_name") String userName
//    );
}
