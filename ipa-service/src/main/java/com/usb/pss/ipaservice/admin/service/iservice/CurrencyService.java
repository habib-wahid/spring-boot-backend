package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.response.CurrencyResponse;

import java.util.List;

public interface CurrencyService {
    List<CurrencyResponse> getAllCurrencies();
}
