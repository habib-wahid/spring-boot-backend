package com.usb.pss.ipaservice.admin.controller;

import com.usb.pss.ipaservice.admin.service.iservice.ActionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.usb.pss.ipaservice.common.APIEndpointConstants.ACTION_ENDPOINT;

@RestController
@RequiredArgsConstructor
@RequestMapping(ACTION_ENDPOINT)
@Tag(name = "Action Controller", description = "API Endpoints for user action related operations.")
public class ActionController {
    private final ActionService actionService;

}
