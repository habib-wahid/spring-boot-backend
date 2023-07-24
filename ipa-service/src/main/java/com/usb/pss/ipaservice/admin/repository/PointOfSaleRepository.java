package com.usb.pss.ipaservice.admin.repository;

import com.usb.pss.ipaservice.admin.model.entity.PointOfSale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface PointOfSaleRepository extends JpaRepository<PointOfSale, Long> {
    Boolean existsByName(String name);

    List<PointOfSale> findByIdIn(Collection<Long> ids);
}
