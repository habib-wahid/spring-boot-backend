package com.usb.pss.ipaservice.admin.service.iservice;

import com.usb.pss.ipaservice.admin.dto.request.CreatePointOfSalesRequest;
import com.usb.pss.ipaservice.admin.dto.request.UpdatePointOfSalesRequest;
import com.usb.pss.ipaservice.admin.dto.response.PointOfSaleResponse;
import com.usb.pss.ipaservice.admin.model.entity.PointOfSale;

import java.util.List;

public interface PointOfSalesService {
    List<PointOfSaleResponse> getAllPointOfSales();

    void createPointOfSales(CreatePointOfSalesRequest createPointOfSalesRequest);

    void updatePointOfSales(UpdatePointOfSalesRequest updatePointOfSalesRequest);

    PointOfSaleResponse getPointOfSales(Long pointOfSalesId);

    PointOfSale findPointOfSaleById(Long pointOfSaleId);
}
