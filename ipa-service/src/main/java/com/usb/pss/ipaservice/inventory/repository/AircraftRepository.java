package com.usb.pss.ipaservice.inventory.repository;

import com.usb.pss.ipaservice.inventory.model.entity.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
}
