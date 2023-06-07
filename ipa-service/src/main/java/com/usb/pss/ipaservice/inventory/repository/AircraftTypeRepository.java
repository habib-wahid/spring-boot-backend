package com.usb.pss.ipaservice.inventory.repository;

import com.usb.pss.ipaservice.inventory.model.entity.AircraftType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AircraftTypeRepository extends JpaRepository<AircraftType, Long> {
}
