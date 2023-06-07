package com.usb.pss.ipaservice.inventory.service;

import com.usb.pss.ipaservice.inventory.dto.AircraftDto;
import com.usb.pss.ipaservice.inventory.dto.AircraftTypeDto;
import com.usb.pss.ipaservice.inventory.model.entity.Aircraft;
import com.usb.pss.ipaservice.inventory.model.entity.AircraftType;
import com.usb.pss.ipaservice.inventory.repository.AircraftRepository;
import com.usb.pss.ipaservice.inventory.repository.AircraftTypeRepository;
import com.usb.pss.ipaservice.utils.GenericResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AircraftService {

    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private AircraftTypeRepository aircraftTypeRepository;

    @Autowired
    private AircraftDao aircraftDao;

    public List<AircraftDto> getAircraft(AircraftDto request) {
        List<Aircraft> aircraftModels = new ArrayList<>();
        if (request.getId() != null) {
            Optional<Aircraft> aircraft = aircraftRepository.findById(request.getId());
            if (aircraft.isPresent()) {
                aircraftModels.add(aircraft.get());
            }
        } else {
            aircraftModels = aircraftRepository.findAll();
        }

        List<AircraftDto> aircraftDtoList = new ArrayList<>();
        for (Aircraft model :
                aircraftModels) {
            AircraftDto dto = new AircraftDto();
            BeanUtils.copyProperties(model, dto);
            aircraftDtoList.add(dto);
        }

        return aircraftDtoList;
    }

    public GenericResponse saveAircraft(AircraftDto request) {
        return aircraftDao.saveAircraft(request);
    }

    public List<AircraftTypeDto> getAircraftType(AircraftTypeDto request) {
        List<AircraftType> aircraftTypes = new ArrayList<>();
        if (request.getId() != null) {
            Optional<AircraftType> aircraftType = aircraftTypeRepository.findById(request.getId());
            if (aircraftType.isPresent()) {
                aircraftTypes.add(aircraftType.get());
            }
        } else {
            aircraftTypes = aircraftTypeRepository.findAll();
        }

        List<AircraftTypeDto> aircraftTypeList = new ArrayList<>();
        for (AircraftType model :
                aircraftTypes) {
            AircraftTypeDto dto = new AircraftTypeDto();
            BeanUtils.copyProperties(model, dto);
            aircraftTypeList.add(dto);
        }

        return aircraftTypeList;
    }

    public GenericResponse saveAircraftType(AircraftTypeDto request) {
        return aircraftDao.saveAircraftType(request);
    }
}
