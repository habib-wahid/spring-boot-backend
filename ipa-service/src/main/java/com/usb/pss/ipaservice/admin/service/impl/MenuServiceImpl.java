package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.request.MenuRequest;
import com.usb.pss.ipaservice.admin.dto.response.MenuResponse;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminMenu;
import com.usb.pss.ipaservice.admin.repository.IpaAdminMenuRepository;
import com.usb.pss.ipaservice.admin.service.iservice.MenuService;
import com.usb.pss.ipaservice.common.ExceptionConstant;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
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
        return menuRepository.findAllMenuResponse();
    }

    @Override
    public void deactivateMenu(Long menuId) {
        // will be implemented later if needed
    }

    private void prepareEntity(MenuRequest menuRequest, IpaAdminMenu menu) {
        menu.setName(menuRequest.name());
        menu.setUrl(menuRequest.url());
        menu.setIcon(menuRequest.icon());
        menu.setService(menuRequest.service());
    }

    private void prepareResponse(IpaAdminMenu menu, MenuResponse menuResponse) {
        menuResponse.setId(menu.getId());
        menuResponse.setName(menu.getName());
        menuResponse.setUrl(menu.getUrl());
        menuResponse.setService(menu.getService());
    }
}
