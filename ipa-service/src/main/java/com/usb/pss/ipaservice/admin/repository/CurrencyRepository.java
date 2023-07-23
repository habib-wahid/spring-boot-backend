package com.usb.pss.ipaservice.admin.repository;

import com.usb.pss.ipaservice.admin.model.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Set;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Set<Currency> findByIdIn(Collection<Long> currencyIds);
}
