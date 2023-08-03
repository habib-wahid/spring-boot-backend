package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.response.CurrencyResponse;
import com.usb.pss.ipaservice.admin.model.entity.Currency;
import com.usb.pss.ipaservice.admin.repository.CurrencyRepository;
import com.usb.pss.ipaservice.admin.service.iservice.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Override
    public List<CurrencyResponse> getAllCurrencies() {
        return currencyRepository.findAll().stream().map(this::getCurrencyResponse).toList();
    }

    @Override
    public List<Currency> findAllCurrenciesByIds(Collection<Long> currencyIds) {
        return currencyRepository.findAll();
    }

    @Override
    public List<CurrencyResponse> getAllCurrencyResponses(Collection<Currency> currencies) {
        return currencies
            .stream()
            .map(this::getCurrencyResponse)
            .toList();
    }


    private CurrencyResponse getCurrencyResponse(Currency currency) {
        CurrencyResponse currencyResponse = new CurrencyResponse();
        currencyResponse.setId(currency.getId());
        currencyResponse.setCode(currency.getCode());
        currencyResponse.setName(currency.getName());
        return currencyResponse;
    }
}
