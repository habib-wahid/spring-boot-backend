package com.usb.pss.ipaservice.admin.controller;

import com.usb.pss.ipaservice.admin.dto.request.GroupRequest;
import com.usb.pss.ipaservice.admin.service.iservice.GroupService;
import com.usb.pss.ipaservice.common.GlobalApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.usb.pss.ipaservice.common.APIEndpointConstants.GROUP_ENDPOINT;

@RestController
@RequestMapping(GROUP_ENDPOINT)
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public GlobalApiResponse<Object> createNewGroup(@RequestBody @Validated GroupRequest request) {
        groupService.createNewGroup(request);
        return new GlobalApiResponse<>(
                HttpStatus.CREATED, "Group created successfully!", null
        );
    }
}
