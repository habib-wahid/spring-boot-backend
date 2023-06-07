package com.usb.pss.ipaservice.inventory.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "aircraft_type")
public class AircraftType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "avg_man_weight")
    private double avgManWeight;

    @Column(name = "avg_woman_weight")
    private double avgWomanWeight;

    @Column(name = "avg_child_weight")
    private double avgChildWeight;

    @Column(name = "avg_infant_weight")
    private double avgInfantWeight;
}
