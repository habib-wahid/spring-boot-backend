package com.usb.pss.ipaservice.admin.controller;

import com.usb.pss.ipaservice.admin.dto.request.GroupRequest;
import com.usb.pss.ipaservice.admin.service.GroupService;
import com.usb.pss.ipaservice.utils.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.usb.pss.ipaservice.common.APIEndpointConstants.GROUP_ENDPOINT;

@RestController
@RequestMapping(GROUP_ENDPOINT)
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public GenericResponse createNewGroup(@RequestBody @Validated GroupRequest request) {
        return groupService.createNewGroup(request);
    }
}
