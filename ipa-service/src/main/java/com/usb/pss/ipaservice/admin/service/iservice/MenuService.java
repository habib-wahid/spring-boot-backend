package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.response.MenuResponse;
import com.usb.pss.ipaservice.admin.dto.response.MenuResponseWithIdName;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminMenu;

import com.usb.pss.ipaservice.admin.model.entity.IpaAdminUser;
import java.util.List;
import java.util.Set;

/**
 * @author Junaid Khan Pathan
 * @date Jun 17, 2023
 */

public interface MenuService {
    IpaAdminMenu getMenuById(Long menuId);

    IpaAdminMenu getMenuByName(String menuName);

    IpaAdminMenu getMenuByUrl(String menuUrl);

    MenuResponse getMenuResponseById(Long menuId);

    List<MenuResponse> getAllMenuResponse();

    void deactivateMenu(Long menuId);

    Set<IpaAdminMenu> getAllMenuByIds(List<Long> menuIds);

    void removeUserMenu(IpaAdminUser user, Set<IpaAdminMenu> menuSet);

    void addUserMenu(IpaAdminUser user, Set<IpaAdminMenu> menuSet);

    List<MenuResponseWithIdName> getAllMenuResponseWithIdName();
}
