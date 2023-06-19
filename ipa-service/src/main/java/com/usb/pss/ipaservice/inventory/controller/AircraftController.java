package com.usb.pss.ipaservice.inventory.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.usb.pss.ipaservice.common.APIEndpointConstants.AIRCRAFT_ENDPOINT;

@RestController
@RequestMapping(path = AIRCRAFT_ENDPOINT)
public class AircraftController {

    @GetMapping
    public String getAircraft() {

        return "Welcome to aircraft controller...";
    }

}
