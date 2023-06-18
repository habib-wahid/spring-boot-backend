package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.request.MenuRequest;
import com.usb.pss.ipaservice.admin.dto.response.MenuResponse;
import com.usb.pss.ipaservice.admin.model.entity.IpaAdminMenu;
import com.usb.pss.ipaservice.admin.repository.IpaAdminMenuRepository;
import com.usb.pss.ipaservice.admin.service.iservice.MenuService;
import com.usb.pss.ipaservice.common.ExceptionConstant;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import com.usb.pss.ipaservice.exception.RuleViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final IpaAdminMenuRepository menuRepository;

    @Override
    public void createNewMenu(MenuRequest menuRequest) {
        Optional<IpaAdminMenu> duplicateMenuName = menuRepository.findActiveMenuByName(menuRequest.name());
        if (duplicateMenuName.isPresent()) {
            throw new RuleViolationException(ExceptionConstant.DUPLICATE_MENU_NAME);
        } else {
            Optional<IpaAdminMenu> duplicateMenuUrl = menuRepository.findActiveMenuByUrl(menuRequest.url());
            if (duplicateMenuUrl.isPresent()) {
                throw new RuleViolationException(ExceptionConstant.DUPLICATE_MENU_URL);
            } else {
                IpaAdminMenu menuToSave = new IpaAdminMenu();
                prepareEntity(menuRequest, menuToSave);
                menuRepository.save(menuToSave);
            }
        }
    }

    @Override
    public IpaAdminMenu getMenuById(Long menuId) {
        Optional<IpaAdminMenu> menu = menuRepository.findActiveMenuById(menuId);
        if (menu.isPresent()) {
            return menu.get();
        } else {
            throw new ResourceNotFoundException(ExceptionConstant.MENU_NOT_FOUND);
        }
    }

    @Override
    public IpaAdminMenu getMenuByName(String menuName) {
        Optional<IpaAdminMenu> menu = menuRepository.findActiveMenuByName(menuName);
        if (menu.isPresent()) {
            return menu.get();
        } else {
            throw new ResourceNotFoundException(ExceptionConstant.MENU_NOT_FOUND);
        }
    }

    @Override
    public IpaAdminMenu getMenuByUrl(String menuUrl) {
        Optional<IpaAdminMenu> menu = menuRepository.findActiveMenuByUrl(menuUrl);
        if (menu.isPresent()) {
            return menu.get();
        } else {
            throw new ResourceNotFoundException(ExceptionConstant.MENU_NOT_FOUND);
        }
    }

    @Override
    public MenuResponse getMenuResponseById(Long menuId) {
        IpaAdminMenu menu = getMenuById(menuId);
        MenuResponse menuResponse = new MenuResponse();
        prepareResponse(menu, menuResponse);
        return menuResponse;
    }

    @Override
    public List<MenuResponse> getAllActiveMenus() {
        return menuRepository.findAllActiveMenus().stream()
                .map(menu -> {
                    MenuResponse menuResponse = new MenuResponse();
                    prepareResponse(menu, menuResponse);
                    return menuResponse;
                }).collect(Collectors.toList());
    }

    @Override
    public List<MenuResponse> getAllInactiveMenus() {
        return menuRepository.findAllInactiveMenus().stream()
                .map(menu -> {
                    MenuResponse menuResponse = new MenuResponse();
                    prepareResponse(menu, menuResponse);
                    return menuResponse;
                }).collect(Collectors.toList());
    }

    @Override
    public void updateMenu(MenuRequest menuRequest, Long menuId) {
        IpaAdminMenu menuToUpdate = getMenuById(menuId);
        Optional<IpaAdminMenu> duplicateMenuName = menuRepository.findActiveMenuByName(menuRequest.name());
        if (duplicateMenuName.isPresent()) {
            throw new RuleViolationException(ExceptionConstant.DUPLICATE_MENU_NAME);
        } else {
            Optional<IpaAdminMenu> duplicateMenuUrl = menuRepository.findActiveMenuByUrl(menuRequest.name());
            if (duplicateMenuUrl.isPresent()) {
                throw new RuleViolationException(ExceptionConstant.DUPLICATE_MENU_URL);
            } else {
                prepareEntity(menuRequest, menuToUpdate);
                menuRepository.save(menuToUpdate);
            }
        }
    }

    @Override
    public void deactivateMenu(Long menuId) {
        IpaAdminMenu menuToDeactivate = getMenuById(menuId);
        menuToDeactivate.setActive(false);
        menuRepository.save(menuToDeactivate);
    }

    private void prepareEntity(MenuRequest menuRequest, IpaAdminMenu menu) {
        menu.setName(menuRequest.name());
        menu.setUrl(menuRequest.url());
        menu.setIcon(menuRequest.icon());
        menu.setService(menuRequest.service());
        menu.setActive(menuRequest.active());
    }

    private void prepareResponse(IpaAdminMenu menu, MenuResponse menuResponse) {
        menuResponse.setId(menu.getId());
        menuResponse.setName(menu.getName());
        menuResponse.setUrl(menu.getUrl());
        menuResponse.setService(menu.getService());
        menuResponse.setActive(menu.isActive());
    }
}
