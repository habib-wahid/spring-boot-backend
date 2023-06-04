package com.usba.pss.aircraft.repository;

import com.usba.pss.aircraft.model.AircraftType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AircraftTypeRepository extends JpaRepository<AircraftType, Long> {
}
