package com.usba.pss.aircraft.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AircraftDto {
    private Long id;

    private String name;

    private int totalSeats;
}
