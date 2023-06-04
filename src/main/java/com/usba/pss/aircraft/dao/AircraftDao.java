package com.usba.pss.aircraft.dao;

import com.usba.pss.aircraft.dto.AircraftDto;
import com.usba.pss.aircraft.dto.AircraftTypeDto;
import com.usba.pss.aircraft.model.Aircraft;
import com.usba.pss.aircraft.model.AircraftType;
import com.usba.pss.aircraft.repository.AircraftRepository;
import com.usba.pss.aircraft.repository.BaseRepository;
import com.usba.pss.utils.GenericResponse;
import com.usba.pss.utils.ResponseCode;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AircraftDao {

    @Autowired
    private BaseRepository baseRepository;

    @Autowired
    private AircraftRepository aircraftRepository;

    @Transactional
    public GenericResponse saveAircraft(AircraftDto request) {
        try {
            if (request != null && request.getName() != null) {
                Aircraft aircraft = new Aircraft();
                if (request.getId() != null) {
                    if (aircraftRepository.findById(request.getId()).isPresent()) {
                        BeanUtils.copyProperties(request, aircraft);
                        baseRepository.merge(aircraft);
                        return new GenericResponse();
                    } else {
                        return new GenericResponse(ResponseCode.DATA_NOT_FOUND.getCode(), "No aircraft found with this id");
                    }
                } else {
                    BeanUtils.copyProperties(request, aircraft);
                    baseRepository.persist(aircraft);
                    return new GenericResponse();
                }
            } else {
                return new GenericResponse(ResponseCode.BAD_REQUEST.getCode(), "Bad request");
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return new GenericResponse(ResponseCode.GENERAL_ERROR.getCode(), "Error occurred. Please try again");
        }
    }

    @Transactional
    public GenericResponse saveAircraftType(AircraftTypeDto request) {
        try {
            if (request != null && request.getName() != null) {
                AircraftType aircraftType = new AircraftType();
                if (request.getId() != null) {
                    if (aircraftRepository.findById(request.getId()).isPresent()) {
                        BeanUtils.copyProperties(request, aircraftType);
                        baseRepository.merge(aircraftType);
                        return new GenericResponse();
                    } else {
                        return new GenericResponse(ResponseCode.DATA_NOT_FOUND.getCode(), "No aircraft type found with this id");
                    }
                } else {
                    BeanUtils.copyProperties(request, aircraftType);
                    baseRepository.persist(aircraftType);
                    return new GenericResponse();
                }
            } else {
                return new GenericResponse(ResponseCode.BAD_REQUEST.getCode(), "Bad request");
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return new GenericResponse(ResponseCode.GENERAL_ERROR.getCode(), "Error occurred. Please try again");
        }
    }
}
