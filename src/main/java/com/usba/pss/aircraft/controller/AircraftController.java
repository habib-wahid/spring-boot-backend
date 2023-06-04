package com.usba.pss.aircraft.controller;

import com.usba.pss.aircraft.dto.AircraftDto;
import com.usba.pss.aircraft.dto.AircraftTypeDto;
import com.usba.pss.aircraft.service.AircraftService;
import com.usba.pss.utils.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
