package com.usb.pss.ipaservice.admin.service.impl;

import com.usb.pss.ipaservice.admin.dto.request.CreatePointOfSalesRequest;
import com.usb.pss.ipaservice.admin.dto.request.UpdatePointOfSalesRequest;
import com.usb.pss.ipaservice.admin.dto.response.PointOfSaleResponse;
import com.usb.pss.ipaservice.admin.model.entity.PointOfSale;
import com.usb.pss.ipaservice.admin.repository.PointOfSaleRepository;
import com.usb.pss.ipaservice.admin.service.iservice.PointOfSalesService;
import com.usb.pss.ipaservice.common.constants.ExceptionConstant;
import com.usb.pss.ipaservice.exception.ResourceAlreadyExistsException;
import com.usb.pss.ipaservice.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.usb.pss.ipaservice.common.constants.ExceptionConstant.DUPLICATE_POINT_OF_SALES;

@Service
@RequiredArgsConstructor
public class PointOfSalesServiceImpl implements PointOfSalesService {
    private final PointOfSaleRepository pointOfSaleRepository;

    @Override
    public List<PointOfSaleResponse> getAllPointOfSales() {
        return getPointOfSalesResponses(pointOfSaleRepository.findAll());
    }

    @Override
    public void createPointOfSales(CreatePointOfSalesRequest createPointOfSalesRequest) {
        if (!pointOfSaleRepository.existsByName(createPointOfSalesRequest.name())) {
            PointOfSale pointOfSale = new PointOfSale();
            pointOfSale.setName(createPointOfSalesRequest.name());
            pointOfSaleRepository.save(pointOfSale);
        } else {
            throw new ResourceAlreadyExistsException(DUPLICATE_POINT_OF_SALES);
        }
    }

    @Override
    public void updatePointOfSales(UpdatePointOfSalesRequest updatePointOfSalesRequest) {
        PointOfSale pointOfSale = getPointOfSale(updatePointOfSalesRequest.id());
        if (!pointOfSaleRepository.existsByName(updatePointOfSalesRequest.name())) {
            pointOfSale.setName(updatePointOfSalesRequest.name());
            pointOfSaleRepository.save(pointOfSale);
        } else {
            throw new ResourceAlreadyExistsException(DUPLICATE_POINT_OF_SALES);
        }
    }

    @Override
    public PointOfSaleResponse getPointOfSales(Long pointOfSalesId) {
        return getPointOfSaleResponseFromPointOfSale(getPointOfSale(pointOfSalesId));
    }

    @Override
    public PointOfSale findPointOfSaleById(Long pointOfSaleId) {
        return getPointOfSale(pointOfSaleId);
    }

    private PointOfSale getPointOfSale(Long pointOfSalesId) {
        return pointOfSaleRepository.findById(pointOfSalesId)
            .orElseThrow(() -> new ResourceNotFoundException(ExceptionConstant.POINT_OF_SALES_NOT_FOUND));
    }

    private PointOfSaleResponse getPointOfSaleResponseFromPointOfSale(PointOfSale pointOfSale) {
        PointOfSaleResponse pointOfSaleResponse = new PointOfSaleResponse();
        pointOfSaleResponse.setId(pointOfSale.getId());
        pointOfSaleResponse.setPointOfSaleName(pointOfSale.getName());
        return pointOfSaleResponse;
    }

    private List<PointOfSaleResponse> getPointOfSalesResponses(List<PointOfSale> pointOfSales) {
        return pointOfSales.stream()
            .map(this::getPointOfSaleResponseFromPointOfSale)
            .toList();
    }
}
