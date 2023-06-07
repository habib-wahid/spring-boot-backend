package com.usb.pss.ipaservice.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AircraftTypeDto {
    private Long id;
    private String name;
    private double avgManWeight;
    private double avgWomanWeight;
    private double avgChildWeight;
    private double avgInfantWeight;
}
