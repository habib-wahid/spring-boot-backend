package com.usb.pss.ipaservice.admin.controller;

import com.usb.pss.ipaservice.admin.dto.response.CurrencyResponse;
import com.usb.pss.ipaservice.admin.service.iservice.CurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.usb.pss.ipaservice.common.constants.APIEndpointConstants.CURRENCY_ENDPOINT;

@RestController
@RequiredArgsConstructor
@RequestMapping(CURRENCY_ENDPOINT)
@Tag(name = "Currency Controller", description = "API Endpoints for currency related operations.")
public class CurrencyController {
    private final CurrencyService currencyService;

    @GetMapping
    @Operation(summary = "Get all currencies")
    public List<CurrencyResponse> getAllCurrencies() {
        return currencyService.getAllCurrencies();
    }
}
