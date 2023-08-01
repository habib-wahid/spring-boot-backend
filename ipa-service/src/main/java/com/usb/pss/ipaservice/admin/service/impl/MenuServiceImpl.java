package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.response.MenuActionResponse;
import com.usb.pss.ipaservice.admin.dto.response.MenuResponse;
import com.usb.pss.ipaservice.admin.model.entity.Menu;
import com.usb.pss.ipaservice.admin.repository.MenuRepository;
import com.usb.pss.ipaservice.admin.service.iservice.MenuService;
import com.usb.pss.ipaservice.common.constants.ExceptionConstant;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;

    @Override
    public Menu getMenuById(Long menuId) {
        return menuRepository.findById(menuId)
            .orElseThrow(() -> new ResourceNotFoundException(ExceptionConstant.MENU_NOT_FOUND));
    }

    @Override
    public MenuActionResponse getMenuResponseById(Long menuId) {
        Menu menu = getMenuById(menuId);
        MenuActionResponse menuActionResponse = new MenuActionResponse();
        prepareResponse(menu, menuActionResponse);
        return menuActionResponse;
    }

    @Override
    public List<MenuActionResponse> getAllMenuResponse() {
        return menuRepository.findAll().stream()
            .map(menu -> {
                MenuActionResponse menuActionResponse = new MenuActionResponse();
                prepareResponse(menu, menuActionResponse);
                return menuActionResponse;
            }).toList();
    }

    @Override
    public void deactivateMenu(Long menuId) {
        // will be implemented later if needed
    }

    @Override
    public List<MenuResponse> getAllMenuResponseWithIdName() {
        return menuRepository.findAll().stream()
            .map(menu -> {
                MenuResponse responseWithIdName = new MenuResponse();
                prepareResponseWithIdName(menu, responseWithIdName);
                return responseWithIdName;
            }).toList();
    }

    @Override
    public void prepareResponse(Menu menu, MenuActionResponse menuActionResponse) {
        menuActionResponse.setId(menu.getId());
        menuActionResponse.setName(menu.getName());
        menuActionResponse.setUrl(menu.getUrl());
        menuActionResponse.setIcon(menu.getIcon());

    }

    private void prepareResponseWithIdName(Menu menu, MenuResponse menuResponse) {
        menuResponse.setId(menu.getId());
        menuResponse.setName(menu.getName());
    }
}
