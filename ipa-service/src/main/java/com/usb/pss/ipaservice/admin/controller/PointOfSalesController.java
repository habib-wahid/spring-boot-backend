package com.usb.pss.ipaservice.admin.controller;

import com.usb.pss.ipaservice.admin.dto.request.CreatePointOfSalesRequest;
import com.usb.pss.ipaservice.admin.dto.request.UpdatePointOfSalesRequest;
import com.usb.pss.ipaservice.admin.dto.response.PointOfSaleResponse;
import com.usb.pss.ipaservice.admin.service.iservice.PointOfSalesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.usb.pss.ipaservice.common.constants.APIEndpointConstants.POINT_OF_SALE_ENDPOINT;

@RestController
@RequiredArgsConstructor
@RequestMapping(POINT_OF_SALE_ENDPOINT)
@Tag(name = "Point of Sales Controller", description = "API Endpoints for point of sale related operations.")
public class PointOfSalesController {
    private final PointOfSalesService pointOfSalesService;

    @GetMapping("/{pointOfSaleId}")
    @Operation(summary = "Get Point Of Sale with its ID")
    public PointOfSaleResponse getPointOfSaleResponse(@Validated @PathVariable Long pointOfSaleId) {
        return pointOfSalesService.getPointOfSales(pointOfSaleId);
    }

    @GetMapping
    @Operation(summary = "Get all Point of Sales")
    public List<PointOfSaleResponse> getAllPointOfSales() {
        return pointOfSalesService.getAllPointOfSales();
    }

    @PostMapping
    @Operation(summary = "Create a new Point of Sale")
    public void createPointOfSale(@Validated @RequestBody CreatePointOfSalesRequest createPointOfSalesRequest) {
        pointOfSalesService.createPointOfSales(createPointOfSalesRequest);
    }

    @PutMapping
    @Operation(summary = "Update an existing Point Of Sale")
    public void updatePointOfSale(@Validated @RequestBody UpdatePointOfSalesRequest updatePointOfSalesRequest) {
        pointOfSalesService.updatePointOfSales(updatePointOfSalesRequest);
    }
}
