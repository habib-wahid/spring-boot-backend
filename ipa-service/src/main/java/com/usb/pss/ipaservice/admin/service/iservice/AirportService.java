package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.inventory.dto.response.AirportResponse;
import com.usb.pss.ipaservice.inventory.model.entity.Airport;

import java.util.Collection;
import java.util.List;

public interface AirportService {
    AirportResponse findById(Long airportId);

    List<Airport> findAllAirportsByIds(Collection<Long> airportIds);

    List<AirportResponse> getAirportResponsesFromAirports(Collection<Airport> airports);
}
