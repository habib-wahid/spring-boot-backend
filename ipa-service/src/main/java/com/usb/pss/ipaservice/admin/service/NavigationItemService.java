package com.usb.pss.ipaservice.admin.service;

import com.usb.pss.ipaservice.admin.dto.NavigationItemRequest;
import com.usb.pss.ipaservice.admin.repository.IpaAdminMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NavigationItemService {

    @Autowired
    private IpaAdminMenuRepository navigationItemRepository;

    public void saveNavigationItems(List<NavigationItemRequest> payload) {
        List<NavigationItem> list = payload.stream()
                .map(item -> NavigationItem.builder()
                        .label(item.getLabel())
                        .key(item.getKey())
                        .url(item.getUrl())
                        .icon(item.getIcon())
                        .parentId(item.getParentId())
                        .build()
                ).toList();

        navigationItemRepository.saveAll(list);
    }
}
