package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.response.CurrencyResponse;
import com.usb.pss.ipaservice.admin.model.entity.Currency;
import com.usb.pss.ipaservice.admin.repository.CurrencyRepository;
import com.usb.pss.ipaservice.admin.service.iservice.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Override
    public List<CurrencyResponse> getAllCurrencies() {
        return currencyRepository.findAll()
            .stream()
            .map(this::getCurrencyResponse)
            .collect(Collectors.toList());
    }

    private CurrencyResponse getCurrencyResponse(Currency currency) {
        CurrencyResponse currencyResponse = new CurrencyResponse();
        currencyResponse.setId(currency.getId());
        currencyResponse.setCode(currency.getCode());
        return currencyResponse;
    }
}
