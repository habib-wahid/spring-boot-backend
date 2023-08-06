package com.usb.pss.ipaservice.admin.repository;

import com.usb.pss.ipaservice.admin.model.entity.AccessLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface AccessLevelRepository extends JpaRepository<AccessLevel, Long> {
    List<AccessLevel> findByIdIn(Collection<Long> accessLevelIds);
    Boolean existsByName(String name);
}
