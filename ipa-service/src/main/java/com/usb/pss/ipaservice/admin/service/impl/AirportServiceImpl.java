package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.service.iservice.AirportService;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import com.usb.pss.ipaservice.inventory.dto.response.AirportResponse;
import com.usb.pss.ipaservice.inventory.model.entity.Airport;
import com.usb.pss.ipaservice.inventory.repository.AirportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

import static com.usb.pss.ipaservice.common.constants.ExceptionConstant.AIRPORT_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {
    private final AirportRepository airportRepository;

    @Override
    public Airport findAirportById(Long airportId) {
        return airportRepository.findById(airportId)
            .orElseThrow(() -> new ResourceNotFoundException(AIRPORT_NOT_FOUND));
    }

    @Override
    public AirportResponse getAirportById(Long airportId) {
        Airport airport = findAirportById(airportId);
        return new AirportResponse(airport.getId(), airport.getName());
    }

    @Override
    public List<Airport> findAllAirportsByIds(Collection<Long> airportIds) {
        return airportRepository.findByIdIn(airportIds);
    }

    @Override
    public List<AirportResponse> getAirportResponses(Collection<Airport> airports) {
        return airports
            .stream()
            .map(this::getAirportResponseFromAirport)
            .toList();
    }

    private AirportResponse getAirportResponseFromAirport(Airport airport) {
        return new AirportResponse(airport.getId(), airport.getName());
    }


}
