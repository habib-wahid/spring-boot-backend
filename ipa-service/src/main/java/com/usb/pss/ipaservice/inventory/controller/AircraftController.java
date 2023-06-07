package com.usb.pss.ipaservice.inventory.controller;

import com.usb.pss.ipaservice.inventory.dto.AircraftDto;
import com.usb.pss.ipaservice.inventory.dto.AircraftTypeDto;
import com.usb.pss.ipaservice.inventory.service.AircraftService;
import com.usb.pss.ipaservice.utils.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/aircraft")
@CrossOrigin(origins = "*")
public class AircraftController {

    @Autowired
    private AircraftService aircraftService;

    @PostMapping(path = "/get")
    @PreAuthorize("hasAuthority('aircraft')")
    public List<AircraftDto> getAircraft(@RequestBody AircraftDto dto) {
        return aircraftService.getAircraft(dto);
    }

    @PostMapping(path = "/add")
    @PreAuthorize("hasAuthority('aircraft')")
    public GenericResponse saveAircraft(@RequestBody AircraftDto dto) {
        return aircraftService.saveAircraft(dto);
    }

    @PostMapping(path = "/type/get")
    @PreAuthorize("hasAuthority('aircraftType')")
    public List<AircraftTypeDto> getAircraftType(@RequestBody AircraftTypeDto dto) {
        return aircraftService.getAircraftType(dto);
    }

    @PostMapping(path = "/type/add")
    @PreAuthorize("hasAuthority('aircraftType')")
    public GenericResponse saveAircraftType(@RequestBody AircraftTypeDto dto) {
        return aircraftService.saveAircraftType(dto);
    }
}
