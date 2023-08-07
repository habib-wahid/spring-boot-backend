package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.response.CurrencyResponse;
import com.usb.pss.ipaservice.admin.model.entity.Currency;

import java.util.Collection;
import java.util.List;

public interface CurrencyService {
    List<CurrencyResponse> getAllCurrencies();

    List<Currency> findAllCurrenciesByIds(Collection<Long> currencyIds);

    List<CurrencyResponse> getAllCurrencyResponses(Collection<Currency> currencies);
}
