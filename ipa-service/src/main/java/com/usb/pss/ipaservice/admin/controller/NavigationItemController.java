package com.usb.pss.ipaservice.admin.controller;

import com.usb.pss.ipaservice.admin.dto.NavigationItemRequest;
import com.usb.pss.ipaservice.admin.service.NavigationItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/navigation-item")
public class NavigationItemController {

    @Autowired
    private NavigationItemService navigationItemService;

    @PostMapping
    public void saveNavigationItems(@RequestBody List<NavigationItemRequest> payload) {
        navigationItemService.saveNavigationItems(payload);
    }

}
