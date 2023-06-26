package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.response.MenuResponse;
import com.usb.pss.ipaservice.admin.dto.response.MenuResponseWithIdName;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminMenu;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminUser;
import com.usb.pss.ipaservice.admin.repository.IpaAdminMenuRepository;
import com.usb.pss.ipaservice.admin.service.iservice.MenuService;
import com.usb.pss.ipaservice.common.ExceptionConstant;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final IpaAdminMenuRepository menuRepository;

    @Override
    public IpaAdminMenu getMenuById(Long menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionConstant.MENU_NOT_FOUND));
    }

    @Override
    public IpaAdminMenu getMenuByName(String menuName) {
        return menuRepository.findByNameIgnoreCase(menuName)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionConstant.MENU_NOT_FOUND));
    }

    @Override
    public IpaAdminMenu getMenuByUrl(String menuUrl) {
        return menuRepository.findByUrlIgnoreCase(menuUrl)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionConstant.MENU_NOT_FOUND));
    }

    @Override
    public MenuResponse getMenuResponseById(Long menuId) {
        IpaAdminMenu menu = getMenuById(menuId);
        MenuResponse menuResponse = new MenuResponse();
        prepareResponse(menu, menuResponse);
        return menuResponse;
    }

    @Override
    public List<MenuResponse> getAllMenuResponse() {
        return menuRepository.findAll().stream()
                .map(menu -> {
                    MenuResponse menuResponse = new MenuResponse();
                    prepareResponse(menu, menuResponse);
                    return menuResponse;
                }).toList();
    }

    @Override
    public void deactivateMenu(Long menuId) {
        // will be implemented later if needed
    }

    @Override
    public Set<IpaAdminMenu> getAllMenuByIds(List<Long> menuIds) {
        return new HashSet<>(menuRepository.findAllById(menuIds));
    }

    @Override
    public void removeUserMenu(IpaAdminUser user, Set<IpaAdminMenu> menuSet) {
        user.getPermittedMenu().removeAll(menuSet);
    }

    @Override
    public void addUserMenu(IpaAdminUser user, Set<IpaAdminMenu> menuSet) {
        user.getPermittedMenu().addAll(menuSet);
    }

    @Override
    public List<MenuResponseWithIdName> getAllMenuResponseWithIdName() {
        return menuRepository.findAll().stream()
            .map(menu -> {
                MenuResponseWithIdName responseWithIdName = new MenuResponseWithIdName();
                prepareResponseWithIdName(menu, responseWithIdName);
                return responseWithIdName;
            }).toList();
    }

    public void prepareResponse(IpaAdminMenu menu, MenuResponse menuResponse) {
        menuResponse.setId(menu.getId());
        menuResponse.setName(menu.getName());
        menuResponse.setUrl(menu.getUrl());
        menuResponse.setIcon(menu.getIcon());
        menuResponse.setServiceId(menu.getModule().getId());
        menuResponse.setServiceName(menu.getModule().getName());
    }

    private void prepareResponseWithIdName(IpaAdminMenu menu, MenuResponseWithIdName menuResponseWithIdName) {
        menuResponseWithIdName.setId(menu.getId());
        menuResponseWithIdName.setName(menu.getName());
    }
}
