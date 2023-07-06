package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.response.MenuResponse;
import com.usb.pss.ipaservice.admin.dto.response.MenuResponseWithIdName;
import com.usb.pss.ipaservice.admin.model.entity.Menu;

import com.usb.pss.ipaservice.admin.model.entity.User;

import java.util.List;
import java.util.Set;

/**
 * @author Junaid Khan Pathan
 * @date Jun 17, 2023
 */

public interface MenuService {
    Menu getMenuById(Long menuId);

    Menu getMenuByName(String menuName);

    Menu getMenuByUrl(String menuUrl);

    MenuResponse getMenuResponseById(Long menuId);

    List<MenuResponse> getAllMenuResponse();

    void deactivateMenu(Long menuId);

    Set<Menu> getAllMenuByIds(List<Long> menuIds);

    void removeUserMenu(User user, Set<Menu> menuSet);

    void addUserMenu(User user, Set<Menu> menuSet);

    List<MenuResponseWithIdName> getAllMenuResponseWithIdName();

    void prepareResponse(Menu menu, MenuResponse menuResponse);
}
