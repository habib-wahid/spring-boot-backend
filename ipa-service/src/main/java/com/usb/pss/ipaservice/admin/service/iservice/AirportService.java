package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.inventory.dto.response.AirportResponse;
import com.usb.pss.ipaservice.inventory.model.entity.Airport;

import java.util.Collection;
import java.util.List;

public interface AirportService {
    Airport findAirportById(Long airportId);

    AirportResponse getAirportById(Long airportId);

    List<Airport> findAllAirportsByIds(Collection<Long> airportIds);

    List<AirportResponse> getAirportResponses(Collection<Airport> airports);
}
